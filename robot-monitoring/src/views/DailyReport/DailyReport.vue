<template>
<v-layout row wrap>
    <v-container fluid>
        <v-flex xs12 style="margin-top:10px;">
        <v-row>
            <v-col
                cols="12"
                lg="3"
            >
                <base-material-stats-card
                color="blue"
                icon="mdi-alpha-t-circle-outline"
                title="Total Count"
                v-model= "count_total"
                />
                 <base-material-stats-card
                color="success"
                icon="mdi-check-circle-outline"
                title="Success Count"
                v-model= "count_success"
                style="margin-top:20px;"
                />
                <base-material-stats-card
                color="red"
                icon="mdi-alert-circle-outline"
                title="Fail Count"
                v-model= "count_fail"
                style="margin-top:20px;"
                />
                <base-material-stats-card
                color="orange"
                icon="mdi-strategy"
                title="Success Rate"
                v-model= "rate_success"
                style="margin-top:20px;"
                />
                
                <v-card
                    class="mx-auto"
                    max-width="400"
                    style="margin-top:20px;"
                >
               
                <v-card-text>
                    <v-row align="center">
                    <v-col
                        cols="6"
                        class="display-5"
                    >
                     <v-text-field
                        :value="startdate"  
                        class="display-5"
                        readonly
                        disabled
                        label="Start Date"
                    >
                    </v-text-field>
                    </v-col>
                    <v-col cols="6">
                        <v-text-field
                        :value="enddate" 
                         class="display-5"
                        readonly
                        disabled
                        label="End Date"
                    >
                    </v-text-field>
                    </v-col>
                    </v-row>
                </v-card-text>    
                </v-card>
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
                lg="4"
            >
               <base-material-card
                    color="#8D6E63"
                    class="px-5 py-3"
                    >
                    <template v-slot:heading style="height:60px;">
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Bot Stats
                        </div>
                    </template>
                    <v-card-text>
                        <v-data-table
                        :headers="headers_Bot"
                        :items="gridData_Bot"
                        dense
                        :footer-props="{
                            'items-per-page-options': [10, 20, 30, -1]
                        }"
                        :items-per-page="-1"
                        hide-default-footer
                        style=" margin-top:-20px;"
                        >
                            <template v-slot:items="props">
                                <td>{{ props.item.bot_name }}({{ props.item.bot_id }})</td>
                                <td style="text-align:right;">{{ props.item.count_total }}</td>
                                <td>{{ props.item.totaluptime }}</td>
                            </template>
                        </v-data-table>
                    </v-card-text>
                </base-material-card>

                <base-material-card
                    color="#FFAB40"
                    class="px-5 py-3"
                    style="margin-top:20px;"
                    >
                    <template v-slot:heading>
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Bot Utilization Rate
                        </div>
                    </template>
                    <v-card-text>
                        <div class="column">
                            <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading">
                                <v-progress-circular width="7" size="70" indeterminate color="red">
                                </v-progress-circular>
                            </div>
                            <doughnut-chart :chart-data="chartData" v-if="!chartLoading" />
                        </div>
                    </v-card-text>
                </base-material-card>
            </v-col>

            <v-col
                cols="12"
                lg="5"
            >
                <base-material-card
                    color="#78909C"
                    class="px-5 py-3"
                    >
                    <template v-slot:heading>
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Job Stats
                        </div>
                    </template>
                    <v-card-text>
                        <v-data-table
                        :headers="headers_Job"
                        :items="gridData_Job"
                        dense
                        :footer-props="{
                            'items-per-page-options': [10, 20, 30, -1]
                        }"
                        :items-per-page="-1"
                        hide-default-footer
                        style=" margin-top:-20px;"
                        />
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
                    lg="6"
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
                                label="Start Date"
                                prepend-icon="event"
                                readonly
                                v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="startdate" @input="datePicker_1 = false" @change="changeStartDate"></v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col
                    cols="12"
                    lg="6"
                    >
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
import DoughnutChart from '@/components/DoughnutChart'

const moment = require('moment')
var startDate = moment().subtract(1, 'days').format('YYYY-MM-DD');
var endDate = moment().subtract(1, 'days').format('YYYY-MM-DD');

export default {
  components: { DoughnutChart },
    data() {
        return {
            startdate: startDate,
            enddate: endDate,
            datePicker_1: false,
            datePicker_2: false,
            dialog: false,
            count_total: "0",
            count_success: "0",
            count_fail: "0",
            rate_success: "0 %",
            headers_Bot: [
                { text: 'Bot', value: 'bot_id' },
                { text: 'Run Cnt', value: 'count_total', align:'end' },
                { text: 'Total Uptime', value: 'totaluptime' }
            ],
            gridData_Bot: [],
            headers_Job: [
                { text: 'Job Name', value: 'job_name' },
                { text: 'Run Cnt', value: 'count_total', align:'end' },
                { text: 'Success Rate', value: 'rate_success', align:'end' },
                { text: 'Last Log Time', value: 'run_date' }
            ],
            gridData_Job: [],
            chartLoading: true, // 데이터를 불러오기 전까지는 progress circle을 사용 
            chartData: [],
        }
    },
    mounted() {
        this.getData()
    },
    methods: {
        getData() {
            this.axios.get(this.$store.state.rOpsApi+'/dailyReport/count?startDate=' + startDate+ '&endDate=' + endDate).then(response => {
                this.count_total = response.data[0].count_total + " EA"
                this.count_success = response.data[0].count_success + " EA"
                this.count_fail = response.data[0].count_fail + " EA"
                this.rate_success = response.data[0].rate_success + " %"
            })
            this.axios.get(this.$store.state.rOpsApi+'/dailyReport/bot?startDate=' + startDate+ '&endDate=' + endDate).then(response => {
                this.gridData_Bot = response.data
            })
            this.axios.get(this.$store.state.rOpsApi+'/dailyReport/bot/utilization?startDate=' + startDate+ '&endDate=' + endDate).then(response => {
                this.chartData = { labels: response.data.labels, chartData: response.data.data }
                this.chartLoading = false
            })
            this.axios.get(this.$store.state.rOpsApi+'/dailyReport/job?startDate=' + startDate+ '&endDate=' + endDate).then(response => {
                this.gridData_Job = response.data
            })
        },
        changeStartDate(date) {
            startDate= date;

            this.getData()
        },
        changeEndDate(date) {
            endDate = date;

            this.getData()
        }
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