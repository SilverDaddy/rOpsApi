<template>
    <v-layout row wrap>
        <v-container fluid>
            <v-data-table
            :headers="headers"
            :items="results"
            class="elevation-1"
            :items-per-page="15"
            >
                <template v-slot:top>
                    <v-toolbar flat color="white">
                    <v-toolbar-title>RPA History Management</v-toolbar-title>
                     <v-divider
                        class="mx-4"
                        inset
                        vertical
                    ></v-divider>
                
                    <span class="subheading" style="font-size:14px; color: grey;">봇 / 업무 / 회차 별 비정상 리스트</span>
                   
                    <div class="flex-grow-1"></div>
                    
                    <v-btn class="ma-2" color="pink" dark @click="deleteHealthCheck">
                        Delete HealthCheck
                        <v-icon dark right>mdi-delete</v-icon>
                    </v-btn>
                    </v-toolbar>
                </template>

                <template v-slot:item.action="{ item }">
                    <v-icon
                    large
                    @click="deleteItem(item)"
                    >
                    delete
                    </v-icon>
                </template>
            </v-data-table>
        </v-container>
    </v-layout>
</template>

<script>
export default {
    data() {
        return {
            headers: [
                { text: 'Bot ID', value: 'bot_id', sortable: false },
                { text: 'Bot Name', value: 'bot_name' },
                { text: 'Job ID', value: 'job_id' },
                { text: 'Job Name', value: 'job_name' },
                { text: 'Seq', value: 'seq_no' },
                { text: 'Step ID', value: 'step_id' },
                { text: 'Step Name', value: 'step_name' },
                { text: 'Run Date', value: 'run_date' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results: [],
        }
    },
    mounted () {
        this.axios.get(this.$store.state.rOpsApi+'/admin/logging/management').then(response => {
            this.results = response.data
        })
    },
    computed: {
    },
    methods: {
        deleteItem (item) {
            const index = this.results.indexOf(item)
            confirm('Are you sure you want to delete this history?') && this.results.splice(index, 1) &&
            this.axios.delete(this.$store.state.rOpsApi+'/admin/logging/'+item.bot_id+'/'+item.job_id+'/'+item.seq_no)
        },
        deleteHealthCheck () {
            confirm('Are you sure you want to delete HealthCheck Job?') &&
            this.axios.delete(this.$store.state.rOpsApi+'/admin/logging/healthcheck')
        }

        
    }
}
</script>

<style scoped>

</style>