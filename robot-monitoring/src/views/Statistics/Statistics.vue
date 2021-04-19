<template>
    <v-layout row wrap>
        <v-container fluid>
            <v-flex xs12>
                <v-card>
                    <v-card-title>
                        <v-toolbar-title>RPA Statistics - Bot Standard</v-toolbar-title>
                        <v-btn text icon x-large color="green" @click="refreshBot">
                            <v-icon>mdi-cached</v-icon>
                        </v-btn>
                        <v-spacer></v-spacer>
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
                                    v-model="startdate_bot"
                                    label="Start Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="startdate_bot" @input="datePicker_1 = false" @change="changeStartDate_bot"></v-date-picker>
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
                                    v-model="enddate_bot"
                                    label="End Date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="enddate_bot" @input="datePicker_2 = false" @change="changeEndDate_bot"></v-date-picker>
                            </v-menu>
                        </v-flex>
                    </v-card-title>
                    <v-data-table
                    :headers="headers_Bot"
                    :items="gridData_Bot"
                    dense
                    >
                        <template v-slot:items="props">
                            <td>{{ props.item.bot_name }}({{ props.item.bot_id }})</td>
                            <td>{{ props.item.job_name }}</td>
                            <td>{{ props.item.step_name }}</td>
                            <td style="text-align:right;">{{ props.item.count_total }}</td>
                            <td>{{ props.item.rate_success }}</td>
                            <td>{{ props.item.totaluptime }}</td>
                            <td>{{ props.item.status_progress }}</td>
                            <td>{{ props.item.run_date }}</td>
                        </template>
                    </v-data-table>
                </v-card>
            </v-flex>

            <v-flex xs12 style="margin-top:25px;">
                <v-card>
                    <v-card-title>
                        <v-toolbar-title>RPA Statistics - Job Standard</v-toolbar-title>
                        <v-btn text icon x-large color="green" @click="refreshJob">
                            <v-icon>mdi-cached</v-icon>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-flex xs12 sm1>
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
                        </v-flex>
                        
                        <div style="margin-right:6px;">~</div>
                       
                        <v-flex xs12 sm1>
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
                        </v-flex>
                    </v-card-title>
                    <v-data-table
                    :headers="headers_Job"
                    :items="gridData_Job"
                    dense
                    :footer-props="{
                        'items-per-page-options': [10, 20, 30, -1]
                    }"
                    :items-per-page="20"
                    >
                        <template v-slot:items="props">
                            <td>{{ props.item.job_name }}</td>
                            <td>{{ props.item.bot_name }}({{ props.item.bot_id }})</td>
                            <td>{{ props.item.step_name }}</td>
                            <td>{{ props.item.count_total }}</td>
                            <td>{{ props.item.rate_success }}</td>
                            <td>{{ props.item.totaluptime }}</td>
                            <td>{{ props.item.status_progress }}</td>
                            <td>{{ props.item.run_date }}</td>
                        </template>
                    </v-data-table>
                </v-card>
            </v-flex>
        </v-container>    
    </v-layout>
</template>

<script>
const moment = require('moment')
var startDate_bot = moment().subtract(30, 'days').format('YYYY-MM-DD');
var endDate_bot = moment().format('YYYY-MM-DD');
var startDate_job = moment().subtract(30, 'days').format('YYYY-MM-DD');
var endDate_job = moment().format('YYYY-MM-DD');

export default {
    data() {
        return {
            startdate_bot: startDate_bot,
            enddate_bot: endDate_bot,
            startdate_job: startDate_job,
            enddate_job: endDate_job,
            datePicker_1: false,
            datePicker_2: false,
            datePicker_3: false,
            datePicker_4: false,
            headers_Bot: [
                { text: 'Bot', value: 'bot_id' },
                { text: 'Last Job Name', value: 'job_name' },
                { text: 'Last Step Name', value: 'step_name' },
                { text: 'Run Cnt', value: 'count_total', align:'end' },
                { text: 'Success Rate', value: 'rate_success', align:'end' },
                { text: 'Total Uptime', value: 'totaluptime' },
                { text: 'In Progress', value: 'status_progress' },
                { text: 'Last Log Time', value: 'run_date' }
            ],
            gridData_Bot: [],
            headers_Job: [
                { text: 'Job Name', value: 'job_name' },
                { text: 'Last Bot', value: 'bot_id' },
                { text: 'Last Step Name', value: 'step_name' },
                { text: 'Run Cnt', value: 'count_total', align:'end' },
                { text: 'Success Rate', value: 'rate_success', align:'end' },
                { text: 'Total Uptime', value: 'totaluptime' },
                { text: 'Avg Time', value: 'avgtime' },
                { text: 'In Progress', value: 'status_progress' },
                { text: 'Last Log Time', value: 'run_date' }
            ],
            gridData_Job: []
        }
    },
    mounted() {
        this.axios.get(this.$store.state.rOpsApi+'/logging/static/bot?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
            this.gridData_Bot = response.data
        })
        this.axios.get(this.$store.state.rOpsApi+'/logging/static/job?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
            this.gridData_Job = response.data
        })
    },
    methods: {
        refreshBot() {
            this.axios.get(this.$store.state.rOpsApi+'/logging/static/bot?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
                this.gridData_Bot = response.data
            })
        },

        refreshJob() {
            this.axios.get(this.$store.state.rOpsApi+'/logging/static/job?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                this.gridData_Job = response.data
            })
        },

        changeStartDate_bot(date) {
            startDate_bot = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/bot?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
                this.gridData_Bot = response.data
            })
        },
        changeEndDate_bot(date) {
            endDate_bot = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/bot?startDate=' + startDate_bot + '&endDate=' + endDate_bot).then(response => {
                this.gridData_Bot = response.data
            })
        },
        changeStartDate_job(date) {
            startDate_job = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/job?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                this.gridData_Job = response.data
            })
        },
        changeEndDate_job(date) {
            endDate_job = date;

            this.axios.get(this.$store.state.rOpsApi+'/logging/static/job?startDate=' + startDate_job + '&endDate=' + endDate_job).then(response => {
                this.gridData_Job = response.data
            })
        }
    }
}
</script>

<style scoped>

</style>