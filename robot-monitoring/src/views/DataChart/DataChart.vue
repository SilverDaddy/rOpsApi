<template>
    <v-layout row wrap>
        <v-container fluid>
            <v-flex xs12>
                <v-card>
                    <v-card-title>                  
                        <v-toolbar-title>
                          <v-radio-group v-model="searchType" label="업무 별 성공률 / 실패 건수" row style="" @change="changeSearchType">
                            <v-radio name="searchType" label="Daily" value="DAILY" ></v-radio>     
                            <v-radio name="searchType" label="Weekly" value="WEEKLY"></v-radio> 
                            <v-radio name="searchType" label="Monthly" value="MONTHLY"></v-radio>                        
                          </v-radio-group>
                        </v-toolbar-title>
                        <v-btn text icon x-large color="green" @click="refresh" style="margin-left:-10px; margin-top:-5px;">
                            <v-icon>mdi-cached</v-icon>
                        </v-btn>
                        <v-spacer></v-spacer>
                    
                        <v-flex xs12 sm3 style="margin-right:20px;">
                            <v-select
                                :items="jobs"
                                v-model="jobsSelected"
                                item-text="job_name"
                                item-value="job_id"
                                label="Select Jobs"
                                multiple
                                chips
                                dense
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

                        <template v-if="searchType === 'DAILY'">
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
                        </template>
                        <template v-if="searchType === 'WEEKLY' || searchType === 'MONTHLY'">
                            <v-select
                                :items="items_year"
                                v-model="select_year"                          
                                label="Year"
                                style="width:0px"
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
                                style="width:0px; padding-left:10px;"
                               @change="changeQuarter($event)"
                            ></v-select>
                        </template>
                    </v-card-title>
                    
                    <div class="columns">    
                      <div class="column">
                        <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading">
                          <v-progress-circular width="7" size="70" indeterminate color="red">
                          </v-progress-circular>
                        </div>
                          <line-chart :chart-data="chartData_successRate" v-if="!chartLoading" />
                      </div>
                      <div class="column">
                        <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading">
                          <v-progress-circular width="7" size="70" indeterminate color="red">
                          </v-progress-circular>
                        </div>
                          <bar-chart :chart-data="chartData_totalCount" v-if="!chartLoading" />
                      </div>
                      <div class="column">
                        <div class="text-lg-center pa-5" style="width: 100%;" v-if="chartLoading">
                          <v-progress-circular width="7" size="70" indeterminate color="red">
                          </v-progress-circular>
                        </div>
                          <bar-chart :chart-data="chartData_failCount" v-if="!chartLoading" />
                      </div>
                  </div>
                </v-card>
            </v-flex>
        </v-container>
    </v-layout>
</template>

    
<script>
import LineChart from '@/components/LineChart'
import BarChart from '@/components/BarChart'

const moment = require('moment')
var jobList = "";
var startDate = moment().subtract(9, 'days').format('YYYY-MM-DD');
var endDate = moment().format('YYYY-MM-DD');
var currentQuarter = moment().quarter();


export default {
   // name: 'List',
    components: {
        LineChart,
        BarChart,
    },
    
    data: () => ({
        searchType: "DAILY",
        startdate: startDate,
        enddate: endDate,
        datePicker_1: false,
        datePicker_2: false,
        jobs: [],
        jobsSelected: [],
        select_quarter: {},
        items_quarter: [],
        select_year: "",
        items_year: [],
        chartLoading: true, // 데이터를 불러오기 전까지는 progress circle을 사용 
        chartData_successRate: [],
        chartData_totalCount: [],
        chartData_failCount: [],
    }),
    created() {
        //this.getJobs()
        this.setSelect_quarter()
        this.setSelect_year()
        //this.getChartData()
    },
    mounted() {
        // job 셀렉트 박스
        this.axios.get(this.$store.state.rOpsApi+'/jobs/selectbox').then(response => {
            this.jobs = response.data
            this.jobsSelected = response.data

            for (var i in this.jobsSelected) {
                if (i==0) jobList = this.jobsSelected[i].job_id
                else jobList += "," + this.jobsSelected[i].job_id 
            }
            // alert(jobList+"/"+this.searchType+"/"+this.select_quarter.value + "/" + quarter+ "/" + year);  
            this.getChartData()
        })
    },
    methods: {
        getJobs() {
             // job 셀렉트 박스
            this.axios.get(this.$store.state.rOpsApi+'/jobs/selectbox').then(response => {
                this.jobs = response.data
                this.jobsSelected = response.data

                for (var i in this.jobsSelected) {
                    if (i==0) jobList = this.jobsSelected[i].job_id
                    else jobList += "," + this.jobsSelected[i].job_id 
                }
            })
        },
        setSelect_year() {
            // 년도 설정
            let years = [];
            for (var i = 2020; i <= new Date().getFullYear(); i++) {
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
        getChartData() {
            //alert(jobList+"/"+this.searchType+ "/" + this.select_quarter.value+ "/" + this.select_year);  
            this.axios.get(this.$store.state.rOpsApi + '/datachart?searchType=' + this.searchType + '&jobs=' + jobList + '&startDate=' + startDate + '&endDate=' + endDate + '&quarter=' + this.select_quarter.value + '&year=' + this.select_year).then(response => {
                this.chartData_successRate = { labels: response.data.labels, chartData: response.data.successRate, labelString: "Success Rate(%)" }
                this.chartData_totalCount = { labels: response.data.labels, chartData: response.data.totalCount, labelString: "Total Count(EA)" }
                this.chartData_failCount = { labels: response.data.labels, chartData: response.data.failCount, labelString: "Fail Count(EA)" }
                this.chartLoading = false
            })
        },
        refresh() {
           this.getChartData()
        },  
        changeSearchType() {
            this.getChartData()
        },
        changeJobs(slJobIdList) {
            jobList = slJobIdList
            this.getChartData()
        },
        changeStartDate(date) {
            startDate = date
            this.getChartData()
        },
        changeEndDate(date) {
            endDate = date
            this.getChartData()
        },
        changeQuarter(event) {
            this.select_quarter = { text: event+' Quater', value: event };
            this.getChartData()
        },
        changeYear(event) {
            this.select_year = event
            this.getChartData()
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
        
    },
}
</script>
<style>
.v-input--radio-group legend.v-label {
  font-weight: bold;
  font-size:20px;
}
</style>