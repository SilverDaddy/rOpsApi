<template>
    <v-layout row wrap>
        <v-container fluid>
            <v-flex xs12>
                <v-card>
                    <v-card-title>                  
                        <v-toolbar-title>RPA Logging History</v-toolbar-title>
                        <v-btn text icon x-large color="green" @click="refresh">
                            <v-icon>mdi-cached</v-icon>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-flex xs12 sm2 style="margin-right:15px;">
                            <v-select
                                :items="bots"
                                v-model="selectedBots"
                                item-text="bot_id"
                                item-value="bot_id"
                                label="Select Bots"
                                multiple
                                chips
                                dense
                                :menu-props="{ maxHeight: '540' }"
                                @change="changeBots"
                            >
                                <template v-slot:prepend-item>                          
                                  <v-list-item
                                    ripple
                                    dense
                                    @click="toggleBot"
                                  >
                                    <v-list-item-action>
                                      <v-icon :color="selectedBots.length > 0 ? 'indigo darken-4' : ''">{{ iconBot }}</v-icon>
                                    </v-list-item-action>
                                    <v-list-item-content>
                                      <v-list-item-title>Select All</v-list-item-title>
                                    </v-list-item-content>
                                  </v-list-item>
                                  <v-divider class="mt-2"></v-divider>
                                </template>
                                <template v-slot:selection="{ item, index }">
                                    <v-chip v-if="index === 0">
                                        <span>{{ item.bot_id }}</span>
                                    </v-chip>
                                    <v-chip v-if="index === 1">
                                        <span>{{ item.bot_id }}</span>
                                    </v-chip>
                                    <span
                                    v-if="index === 2"
                                    class="grey--text caption"
                                    >(+{{ selectedBots.length - 2 }} others)</span>
                                </template>
                            
                            </v-select>
                        </v-flex>
                    
                        <v-flex xs12 sm3 style="margin-right:20px;">
                            <v-select
                                :items="jobs"
                                v-model="jobsSelected"
                                item-text="job_name"
                                item-value="job_id"
                                label="Select Jobs"
                                multiple
                                dense
                                chips
                                :menu-props="{ maxHeight: '540' }"
                                @change="changeJobs"
                            >
                                <template v-slot:prepend-item>
                                  <v-list-item
                                    ripple
                                    dense
                                    @click="toggleJob"
                                  >
                                    <v-list-item-action>
                                      <v-icon :color="jobsSelected.length > 0 ? 'indigo darken-4' : ''">{{ iconJob }}</v-icon>
                                    </v-list-item-action>
                                    <v-list-item-content>
                                      <v-list-item-title>Select All</v-list-item-title>
                                    </v-list-item-content>
                                  </v-list-item>
                                  <v-divider class="mt-2"></v-divider>
                                </template>
                                <template v-slot:selection="{ item, index }">
                                    <v-chip v-if="index === 0">
                                        <span>{{ item.job_name }}</span>
                                    </v-chip>
                                    <span
                                    v-if="index === 1"
                                    class="grey--text caption"
                                    >(+{{ jobsSelected.length - 1 }} others)</span>
                                </template>    
                            </v-select>
                        </v-flex>

                        <v-flex xs12 sm1>
                            <v-menu
                            v-model="datePicker_1"
                            :close-on-content-click="false"
                            :nudge-right="40"
                            lazy
                            transition="scale-transition"
                            offset-y
                            full-width
                            min-width="290px"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                    v-model="startdate"
                                    label="Start Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="startdate" @input="datePicker_1 = false" @change="changeStartDate"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        
                        <div style="margin-right:6px;">~</div>
                       
                        <v-flex xs12 sm1>
                            <v-menu
                            v-model="datePicker_2"
                            :close-on-content-click="false"
                            :nudge-right="40"
                            lazy
                            transition="scale-transition"
                            offset-y
                            full-width
                            min-width="290px"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                    v-model="enddate"
                                    label="End Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="enddate" @input="datePicker_2 = false" @change="changeEndDate"></v-date-picker>
                            </v-menu>
                        </v-flex>

                    </v-card-title>
                    <v-data-table
                    :headers="headers"
                    :items="gridData_History"
                    dense
                    :footer-props="{
                        'items-per-page-options': [20, 30, 40, -1]
                    }"
                    :items-per-page="30"
                    >
                        <template v-slot:item.seq_no="{ item }">
                            <v-chip :color="getColor(item.seq_no)" dark>{{ item.seq_no }}</v-chip>
                        </template>
                        
                        <template v-slot:no-gridData_History>
                            <v-alert :value="true" color="error" icon="warning">
                            Your search for "{{ search }}" found no gridData_History.
                            </v-alert>
                        </template>
                    </v-data-table>
                </v-card>
            </v-flex>
        </v-container>
    </v-layout>
</template>

<script>
const moment = require('moment')
var botList = "";
var jobList = "";
var startDate = moment().subtract(1, 'days').format('YYYY-MM-DD');
var endDate = moment().format('YYYY-MM-DD');
var seq_prev = "";
var seq_current = "";
var resultColor = "#C0AD8B";
var colorChangeFlag = "";

export default {
    data() {
        return {
            
            startdate: startDate,
            enddate: endDate,
            datePicker_1: false,
            datePicker_2: false,
            headers: [
                { text: 'Bot ID', value: 'bot_id', sortable: false },
                { text: 'Job Name', value: 'job_name', sortable: false },
                { text: 'Seq', value: 'seq_no', align:'end', sortable: false },
                { text: 'Step ID', value: 'step_id', sortable: false },
                { text: 'Step Name', value: 'step_name', sortable: false },
                { text: 'Status', value: 'code_name', sortable: false },
                { text: 'Run Date', value: 'run_date', sortable: false },
                { text: 'Elapsed Time', value: 'elapsedTime', sortable: false },
                { text: 'Description', value: 'description', sortable: false }
            ],
            pagination: {rowsPerPage: 10},
            gridData_History: [],
            bots: [],
            selectedBots: [],
            jobs: [],
            jobsSelected: [],
        }
    },
    mounted() {
         // bot 셀렉트 박스
        this.axios.get(this.$store.state.rOpsApi+'/bots').then(response => {
            this.bots = response.data
            this.selectedBots = response.data

            for (var i in this.selectedBots) {
                if (i==0) botList = this.selectedBots[i].bot_id
                else botList += "," + this.selectedBots[i].bot_id 
            }

            // job 셀렉트 박스
            this.axios.get(this.$store.state.rOpsApi+'/jobs/selectbox').then(response => {
                this.jobs = response.data
                this.jobsSelected = response.data

                for (var i in this.jobsSelected) {
                    if (i==0) jobList = this.jobsSelected[i].job_id
                    else jobList += "," + this.jobsSelected[i].job_id 
                }
                // RPA Logging History 테이블
                this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                    this.gridData_History = response.data
                })
            })
        })
        
    },
    methods: {
        refresh() {
            // RPA Logging History 테이블
            this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                this.gridData_History = response.data
            })
        },  

        changeBots(slBotIdList) {
            botList = slBotIdList;

            this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                this.gridData_History = response.data
            })
        },
        changeJobs(slJobIdList) {
            jobList = slJobIdList;

            this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                this.gridData_History = response.data
            })
        },
        changeStartDate(date) {
            startDate = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                this.gridData_History = response.data
            })
        },
        changeEndDate(date) {
            endDate = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging?bots=' + botList + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate).then(response => {
                this.gridData_History = response.data
            })
        },
        toggleJob () {
            this.$nextTick(() => {
                jobList = "";

                if (this.allJob) {
                    this.jobsSelected = []
                } else {
                    this.jobsSelected = this.jobs.slice()
                }
  
                for (var k in this.jobsSelected) {
                    if (jobList == "")  jobList = this.jobsSelected[k].job_id
                    else jobList += ","+this.jobsSelected[k].job_id
                }

                this.changeJobs(jobList);
            })
           
        },
        toggleBot () {
            this.$nextTick(() => {
                botList = "";

                if (this.allBot) {
                    this.selectedBots = []
                } else {
                    this.selectedBots = this.bots.slice()
                }
                for (var k in this.selectedBots) {
                    if (botList == "")  botList = this.selectedBots[k].bot_id
                    else botList += ","+this.selectedBots[k].bot_id
                }

                this.changeBots(botList);
            })
        },
        getColor (seq_no) {
            if (seq_prev == "") {
                seq_prev = seq_no;
            }
            seq_current = seq_no;
            
            if (seq_prev != seq_current) {
                colorChangeFlag = 'Y';
            } else {
                colorChangeFlag = 'N';
            }
            
            if (colorChangeFlag == "Y") {
                if (resultColor == "#C0AD8B") {
                    resultColor = "#7E776A";
                } else {
                    resultColor = "#C0AD8B";
                }
            }
            
            seq_prev = seq_no;

            return resultColor;
        },

    },

    computed: {
        allJob () {
          return this.jobsSelected.length === this.jobs.length
        },
        someJob () {
          return this.jobsSelected.length > 0 && !this.allJob
        },
        iconJob () {
          if (this.allJob) return 'mdi-close-box'
          if (this.someJob) return 'mdi-minus-box'
          return 'mdi-checkbox-blank-outline'
        },
        allBot () {
          return this.selectedBots.length === this.bots.length
        },
        someBot () {
          return this.selectedBots.length > 0 && !this.allBot
        },
        iconBot () {
          if (this.allBot) return 'mdi-close-box'
          if (this.someBot) return 'mdi-minus-box'
          return 'mdi-checkbox-blank-outline'
        },
    },
}
</script>

<style scoped>
</style>