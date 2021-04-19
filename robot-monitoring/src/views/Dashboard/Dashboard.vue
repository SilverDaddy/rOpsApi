<template>
    <v-layout row wrap>
        <v-container fluid class="grey lighten-5">
            <v-row class="mb-6" no-gutters>
                <v-col sm="8" flot>
                    <v-data-table
                    :headers="headers_Job"
                    :items="gridData_Job"
                    :hide-default-footer="true"
                    dark
                    :items-per-page="-1"
                    class="elevation-1"
                    style="width:98%"
                    >
                    <template v-slot:item.rate_success="{ item }">
                        <v-chip :color="getColor(item.rate_success)" dark>{{ item.rate_success }}</v-chip>
                    </template>
                    <template v-slot:item.status_progress="{ item }">
                        <v-chip :color="getColorStatus(item.status_progress)" dark>{{ item.status_progress }}</v-chip>
                    </template>
                    </v-data-table>

                    <v-fab-transition >
                        <v-btn
                        fab
                        large
                        color="#EEEEEE"
                        bottom
                        left
                        style="margin-top:-10px;"
                        @click="dialog = !dialog"
                        >
                        <v-icon>mdi-calendar-cursor</v-icon>
                        </v-btn>
                    </v-fab-transition>
                </v-col>
                <v-col sm="4">
                    <v-data-iterator
                    :items="gridData_Bot"
                    :items-per-page.sync="itemsPerPage"
                    hide-default-footer
                    >
                        <template v-slot:header>
                            <v-toolbar
                                class="mb-2"
                                color="grey"
                                dark
                                flat
                                tile
                            >
                                <v-toolbar-title class="title"> 
                                    Bot Status
                                </v-toolbar-title>
                                <div class="flex-grow-1"></div>
                                <v-toolbar-title class="subtitle-1">
                                    <v-icon color="pink">error</v-icon><span style="padding-right:10px;"> - 점검 필요</span>
                                    <v-icon color="teal">cast_connected</v-icon><span style="padding-right:10px;"> - 대기중</span> 
                                    <v-icon color="blue">play_circle_filled</v-icon><span> - 가동중</span> 
                                </v-toolbar-title>
                            </v-toolbar>
                        </template>
                        <template v-slot:default="props">
                            <v-row>
                                <v-col
                                    v-for="item in props.items"
                                    :key="item.bot_id"
                                    lg="4"
                                >   
                                        <v-chip pill x-large class="body-2" style="overflow:inherit; white-space:inherit;">
                                            <v-avatar left>
                                                <v-icon x-large color="pink" v-if="item.status_progress.indexOf('봇점검 필요') > -1">error</v-icon>
                                                <v-icon large color="teal" v-if="item.status_progress.indexOf('대기중') > -1">cast_connected</v-icon>
                                                <v-icon x-large color="blue" v-if="item.status_progress.indexOf('수행중') > -1">play_circle_filled</v-icon>
                                            </v-avatar>
                                            <div style="text-overflow: ellipsis; overflow: hidde; padding-left: 5px;">
                                            {{ item.bot_id }}({{ item.bot_name }})
                                             </div>
                                        </v-chip>               
                                </v-col>
                            </v-row>
                        </template>
                    </v-data-iterator>

                    

                </v-col>
            </v-row>

            <v-dialog
                v-model="dialog"
                max-width="400px"
            >
                <v-card>
                <v-card-title>
                    Search Date
                </v-card-title>  
                <v-card-text>
                    <v-row>
                        <v-col
                        cols="12"
                        lg="6"
                        >
                            <v-menu
                            v-model="datePicker_3"
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
                                    v-model="startdate_job"
                                    label="Start Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="startdate_job" @input="datePicker_3 = false" @change="changeStartDate_job"></v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col
                        cols="12"
                        lg="6"
                        >
                            <v-menu
                            v-model="datePicker_4"
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
                                    v-model="enddate_job"
                                    label="End Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="enddate_job" @input="datePicker_4 = false" @change="changeEndDate_job"></v-date-picker>
                            </v-menu>
                        </v-col>
                    </v-row>
                </v-card-text>
    
                <v-card-actions style="margin-top:-30px;">
                    <v-spacer></v-spacer>
                    <v-btn
                    text
                    color="primary"
                    @click="dialog = false"
                    >
                    CLOSE
                    </v-btn>
                </v-card-actions>
                </v-card>
            </v-dialog>

        </v-container>                                  
    </v-layout>
</template>

<script>
const moment = require('moment')
var startDate_bot = moment().subtract(1, 'days').format('YYYY-MM-DD');
var endDate_bot = moment().format('YYYY-MM-DD');
var startDate_job = moment().subtract(30, 'days').format('YYYY-MM-DD');
var endDate_job = moment().format('YYYY-MM-DD');

export default {
    data() {
        return {
            dialog: false,
            startdate_bot: startDate_bot,
            enddate_bot: endDate_bot,
            startdate_job: startDate_job,
            enddate_job: endDate_job,
            datePicker_3: false,
            datePicker_4: false,
            itemsPerPage: 100,
            gridData_Bot: [],
            headers_Job: [
                { text: 'Job Name', value: 'job_name' },
                { text: 'Run Cnt', value: 'count_total', align:'end' },
                { text: 'Success Rate(%)', value: 'rate_success', align:'end' },
                { text: 'Total Uptime', value: 'totaluptime' },
                { text: 'Avg Time', value: 'avgtime' },
                { text: 'In Progress', value: 'status_progress' },
            ],
            gridData_Job: [],
            polling: null
        }
    },
    mounted() {
        this.axios.get(this.$store.state.rOpsApi+'/logging/static/jobAll?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
            this.gridData_Job = response.data
        })

        this.axios.get(this.$store.state.rOpsApi+'/logging/static/botAll?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
            this.gridData_Bot = response.data
        })
    },
    methods: {
        getColor (rate_success) {
            if (rate_success < 50) return 'red'
            else if (rate_success > 50 && rate_success < 80) return 'orange'
            else return 'green'
        },
        getColorStatus (status_progress) {
            if (status_progress.indexOf('최신작업 오류') > -1) return 'pink'
            else if (status_progress.indexOf('가동중인 봇 없음') > -1) return ''
            else return 'blue'
        },
        pollData () {
            this.polling = setInterval(() => {
                this.axios.get(this.$store.state.rOpsApi+'/logging/static/jobAll?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                    this.gridData_Job = response.data
                })

                this.axios.get(this.$store.state.rOpsApi+'/logging/static/botAll?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
                    this.gridData_Bot = response.data
                })
            }, 60000)
        },
        changeStartDate_job(date) {
            startDate_job = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/jobAll?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                this.gridData_Job = response.data
            })
        },
        changeEndDate_job(date) {
            endDate_job = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/jobAll?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                this.gridData_Job = response.data
            })
        }
    },
    beforeDestroy () {
        clearInterval(this.polling)
    },
    created () {
        this.pollData()
    }
}
</script>

<style scoped>

</style>