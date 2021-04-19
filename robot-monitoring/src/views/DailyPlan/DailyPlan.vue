<template>
<v-layout row wrap>
    <v-container fluid>
        <v-flex xs12 style="margin-top:10px;">
        <v-row>
            <v-col
                cols="12"
                lg="6"
            >
                <base-material-card
                    color="#4CAF50"
                    class="px-5 py-3"
                    >
                    <template v-slot:heading style="height:60px;">
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        To Do
                        </div>
                    </template>
                    <v-card-text>
                        <v-data-table
                        :headers="headers_1"
                        :items="gridData_1"
                        
                        :footer-props="{
                            'items-per-page-options': [10, 20, 30, -1]
                        }"
                        :items-per-page="-1"
                        hide-default-footer
                        style=" margin-top:-30px;"
                        >
                            <template v-slot:items="props">
                                <td style="text-align:left;">{{ props.item.schedule_name }}</td>
                                <td>{{ props.item.schedule_time }}</td>
                            </template>
                        </v-data-table>
                    </v-card-text>
                </base-material-card>
                <v-fab-transition >
                    <v-btn
                    fab
                    large
                    dark
                    bottom
                    left
                    style="margin-top:-30px;"
                    @click="dialog = !dialog"
                    >
                    <v-icon>mdi-calendar-cursor</v-icon>
                    </v-btn>
                </v-fab-transition>
            </v-col>

            <v-col
                cols="12"
                lg="6"
            >
                <base-material-card
                    color="#00BCD4"
                    class="px-5 py-3"
                    >
                    <template v-slot:heading style="height:60px;">
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Done
                        </div>
                    </template>
                    <v-card-text>
                        <v-data-table
                        :headers="headers_3"
                        :items="gridData_3"
                        
                        :footer-props="{
                            'items-per-page-options': [10, 20, 30, -1]
                        }"
                        :items-per-page="-1"
                        hide-default-footer
                        style=" margin-top:-30px;"
                        >
                            <template v-slot:items="props">
                                <td style="text-align:left;">{{ props.item.job_name }}</td>
                                <td>{{ props.item.run_date }}</td>
                            </template>
                        </v-data-table>
                    </v-card-text>
                </base-material-card>

                <base-material-card
                    color="#FF5722"
                    class="px-5 py-3"
                    style="margin-top:10px;"
                    >
                    <template v-slot:heading style="height:60px;">
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Doing
                        </div>
                    </template>
                    <v-card-text>
                        <v-data-table
                        :headers="headers_2"
                        :items="gridData_2"
                        
                        :footer-props="{
                            'items-per-page-options': [10, 20, 30, -1]
                        }"
                        :items-per-page="-1"
                        hide-default-footer
                        style=" margin-top:-30px;"
                        >
                        <template v-slot:item.job_name="{ item }">
                            <v-chip :color="getColor(item.run_date)">{{ item.job_name }}</v-chip>
                        </template>
                            
                        </v-data-table>
                    </v-card-text>
                </base-material-card>
            </v-col>
        </v-row>
        </v-flex>
        
        
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
                    lg="12"
                    >
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
                                label="Search Date"
                                prepend-icon="event"
                                readonly
                                v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="startdate" @input="datePicker_1 = false" @change="changeStartDate"></v-date-picker>
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
var startDate = moment().format('YYYY-MM-DD');
export default {
    data() {
        return {
            startdate: startDate,
            datePicker_1: false,
            datePicker_2: false,
            dialog: false,
            headers_1: [
                { text: 'Schedule Name', value: 'schedule_name', align:'start'},
                { text: 'Schedule Time', value: 'schedule_time' }
            ],
            gridData_1: [],
            headers_2: [
                { text: 'Job Name', value: 'job_name' },
                { text: 'Schedule Time', value: 'schedule_time' },
                { text: 'Log Time', value: 'run_date' }
            ],
            gridData_2: [],
            headers_3: [
                { text: 'Job Name', value: 'job_name' },
                { text: 'Log Time', value: 'run_date' }
            ],
            gridData_3: [],
            polling: null
        }
    },
    mounted() {
        this.getData()
        this.pollData()
    },
    methods: {
        getData() {
            this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/todo?searchDate=' + startDate).then(response => {
                this.gridData_1 = response.data
            })
            this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/doing?searchDate=' + startDate).then(response => {
                this.gridData_2 = response.data
            })
            this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/done?searchDate=' + startDate).then(response => {
                this.gridData_3 = response.data
            })
        },
        pollData () {
            this.polling = setInterval(() => {
                this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/todo?searchDate=' + startDate).then(response => {
                    this.gridData_1 = response.data
                })
                this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/doing?searchDate=' + startDate).then(response => {
                    this.gridData_2 = response.data
                })
                this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/done?searchDate=' + startDate).then(response => {
                    this.gridData_3 = response.data
                })
            }, 60000)
        },
        changeStartDate(date) {
            startDate= date;

            this.getData()
        },
        getColor (run_date) {
            if (run_date == null) return '#B2DFDB'
            else return '#FF8A80'
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

<style>
.v-card--material__heading {
    height: 75px;
}
.v-icon notranslate mdi {
    font-size: 35px;
}
.display-5 {
    font-size: 29px;
    font-weight: bold;
}
</style>