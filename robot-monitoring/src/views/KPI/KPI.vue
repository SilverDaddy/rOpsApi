<template>
<v-layout row wrap>
    <v-container fluid>
        <v-flex xs12 style="margin-top:10px;">
        <v-row>
            <v-col
                cols="12"
                lg="6"
                style=""
            >
                <div style="height:120px;">
                    <div style="float:left; width:28%;">
                        <base-material-stats-card
                            color="#4DD0E1"
                            icon="work_outline"
                            title="Job"
                            v-model= "count_job"
                        />
                    </div>
                    <div style="float:left; width:32%; padding-left:10px">
                        <base-material-stats-card
                            color="#4DD0E1"
                            icon="done_outline"
                            title="Run Count"
                            v-model= "count_run"
                        />
                    </div>
                    <div style="float:left; width:40%; ; padding-left:10px">
                        <base-material-stats-card
                            color="#4DD0E1"
                            icon="eco"
                            title="Saving Time"
                            v-model= "working_time"
                        />
                    </div>                                       
                </div>
                <base-material-card
                    color="#689F38"
                    class="px-5 py-3"
                    style="margin-top:60px;"
                    >
                    <template v-slot:heading>
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Saving Time - Total
                        </div>
                    </template>
                    <v-card-text>
                        <div class="column">
                            <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading_Total">
                                <v-progress-circular width="7" size="70" indeterminate color="red">
                                </v-progress-circular>
                            </div>
                            <singleLine-chart :chart-data="chartData_Total" v-if="!chartLoading_Total" />
                        </div>
                    </v-card-text>
                </base-material-card>
                <v-card
                    class="mx-auto"
                    style="margin-top:20px;"
                >
                    <v-card-text>
                        <v-row align="center">
                            <v-col
                            cols="5"
                            class="display-5"
                            >
                                <v-radio-group v-model="searchType" readonly disabled row>
                                    <v-radio name="searchType" label="Daily" value="DAILY" ></v-radio>     
                                    <v-radio name="searchType" label="Weekly" value="WEEKLY"></v-radio> 
                                    <v-radio name="searchType" label="Monthly" value="MONTHLY"></v-radio>                        
                                </v-radio-group>
                        </v-col>
                        
                        <v-col
                            cols="3"
                            class="display-5"
                        >
                            <template v-if="searchType === 'DAILY'">
                                <v-text-field
                                :value="startdate"  
                                class="display-5"
                                readonly
                                disabled
                                label="Start Date" />
                            </template>
                            <template v-if="searchType === 'WEEKLY'">
                                <v-text-field
                                :value="select_year"  
                                class="display-5"
                                readonly
                                disabled
                                label="Year" />
                            </template> 
                            <template v-if="searchType === 'MONTHLY'">
                                <v-text-field
                                :value="select_year"  
                                class="display-5"
                                readonly
                                disabled
                                label="Year" />
                            </template>                            
                        </v-col>
                        <v-col
                            cols="3"
                            class="display-5"
                        >
                            <template v-if="searchType === 'DAILY'">
                                <v-text-field
                                :value="enddate" 
                                class="display-5"
                                readonly
                                disabled
                                label="End Date" />
                            </template>
                            <template v-if="searchType === 'WEEKLY'">
                                <v-text-field
                                :value="select_quarter.text"  
                                class="display-5"
                                readonly
                                disabled
                                label="Quater" />
                            </template>   
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
                lg="6"
            >
                <base-material-card
                    color="#F06292"
                    class="px-5 py-3"
                    >
                    <template v-slot:heading>
                        <div class="display-2 font-weight-light" style="font-size:30px!important; margin-top:-15px;">
                        Saving Time - Job
                        </div>
                    </template>
                    <v-card-text>
                        <div class="column">
                            <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading_Job">
                                <v-progress-circular width="7" size="70" indeterminate color="red">
                                </v-progress-circular>
                            </div>
                            <horizontalBar-chart :chart-data="chartData_Job" v-if="!chartLoading_Job" />
                        </div>
                    </v-card-text>
                </base-material-card>
            </v-col>
        </v-row>
        </v-flex>
        
        
        <v-dialog
            v-model="dialog"
            max-width="360px"
          >
            <v-card>
              <v-card-title>
                  Search Date
              </v-card-title>  
              <v-card-text style="margin-top:-20px">
                  <v-row>
                    <v-col
                    cols="12"
                    lg="12"
                    >
                        <v-toolbar-title>
                          <v-radio-group v-model="searchType" label="" row style="" @change="changeSearchType">
                            <v-radio name="searchType" label="Daily" value="DAILY" ></v-radio>     
                            <v-radio name="searchType" label="Weekly" value="WEEKLY"></v-radio> 
                            <v-radio name="searchType" label="Monthly" value="MONTHLY"></v-radio>                        
                          </v-radio-group>
                        </v-toolbar-title>
                        <template v-if="searchType === 'DAILY'">
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
                        
                        </template>
                        <template v-if="searchType === 'WEEKLY' || searchType === 'MONTHLY'">
                            <v-select
                                :items="items_year"
                                v-model="select_year"                          
                                label="Year"
                               
                                @change="changeYear($event)"
                            ></v-select>
                        </template>
                        <template v-if="searchType === 'WEEKLY'">
                            <v-select
                                :items="items_quarter"
                                v-model="select_quarter"
                                item-text="text"
                                item-value="value"
                                label="Quater"
                                style=""
                               @change="changeQuarter($event)"
                            ></v-select>
                        </template>
                    </v-col>
                    <v-col
                    cols="12"
                    lg="12"
                    >
                        
                    </v-col>
                  </v-row>
              </v-card-text>
  
              <v-card-actions style="margin-top:-50px;">
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
import SingleLineChart from '@/components/SingleLineChart'
import HorizontalBarChart from '@/components/HorizontalBarChart'

const moment = require('moment')
var startDate = moment().subtract(9, 'days').format('YYYY-MM-DD');
var endDate = moment().format('YYYY-MM-DD');
var currentQuarter = moment().quarter();

export default {
  components: { SingleLineChart, HorizontalBarChart },
    data() {
        return {
            searchType: "DAILY",
            startdate: startDate,
            enddate: endDate,
            datePicker_1: false,
            datePicker_2: false,
            select_quarter: {},
            items_quarter: [],
            select_year: "",
            items_year: [],
            dialog: false,
            count_job: "0",
            count_run: "0",
            working_time: "0",
            chartLoading_Total: true,
            chartLoading_Job: true, // 데이터를 불러오기 전까지는 progress circle을 사용 
            chartData_Total: [],
            chartData_Job: [],
        }
    },
    created() {
        //this.getJobs()
        this.setSelect_quarter()
        this.setSelect_year()
        //this.getChartData()
    },
    mounted() {
        this.getData()
    },
    methods: {
        setSelect_year() {
            // 년도 설정
            let years = [];
            for (var i = 2000; i <= new Date().getFullYear(); i++) {
                years.push(i);
            }
            this.items_year = years;
            this.select_year = new Date().getFullYear()
        },
        setSelect_quarter() {
            //let text = [];
            //let value = [];
            for (var i = 1; i <= 4; i++) {
                this.items_quarter.push({text:i+" Quarter",value:i})
            }
            this.select_quarter = { text: currentQuarter+' Quater', value: currentQuarter };
        },
        getData() {
            this.axios.get(this.$store.state.rOpsApi+'/kpi/count?searchType=' + this.searchType + '&startDate=' + startDate + '&endDate=' + endDate + '&quarter=' + this.select_quarter.value + '&year=' + this.select_year).then(response => {
                this.count_job = response.data[0].count_job + " EA"
                this.count_run = response.data[0].count_run + " EA"
                this.working_time = response.data[0].working_time
            })
            this.axios.get(this.$store.state.rOpsApi+'/kpi/chartDataByTotal?searchType=' + this.searchType + '&startDate=' + startDate + '&endDate=' + endDate + '&quarter=' + this.select_quarter.value + '&year=' + this.select_year).then(response => {
                this.chartData_Total = { labels: response.data.labels, chartData: response.data.data }
                this.chartLoading_Total = false
            })
            this.axios.get(this.$store.state.rOpsApi+'/kpi/chartDataByJob?searchType=' + this.searchType + '&startDate=' + startDate + '&endDate=' + endDate + '&quarter=' + this.select_quarter.value + '&year=' + this.select_year).then(response => {
                this.chartData_Job = { labels: response.data.labels, chartData: response.data.data }
                this.chartLoading_Job = false
            })
        },
        changeStartDate(date) {
            startDate= date;

            this.getData()
        },
        changeEndDate(date) {
            endDate = date;

            this.getData()
        },
        changeSearchType() {
            this.getData()
        },
        changeQuarter(event) {
            this.select_quarter = { text: event+' Quater', value: event };
            this.getData()
        },
        changeYear(event) {
            this.select_year = event
            this.getData()
        },
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
    font-size:29px;
    font-weight: bold;
}
.display-6 {
    font-size:20px;
    font-weight: bold;
}
.v-card--material-stats > div > .v-card--material__heading {
    width:79px !important;
}
.v-card--material__heading > i {
    margin-left: -14px;
}
.ml-auto > div {
    font-size: 20px !important;
    font-weight: bold !important;
}
div.d-flex.grow.flex-wrap > div.ml-6 > div > h3 {
    font-size: 30px !important;
    font-weight: bold !important;
}
</style>