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
                    <v-toolbar-title>WLM - Queue</v-toolbar-title>
                    <div class="flex-grow-1"></div>
                    </v-toolbar>
                </template>
                
                <template v-slot:item.action="{ item }">
                    <v-icon
                    @click="addWorkItem(item, 'MAIN')"
                    >
                    play_circle_filled
                    </v-icon>
                     <v-icon
                    @click="infoItem(item)"
                    >
                    info
                    </v-icon>
                </template>
            </v-data-table>

            <v-dialog v-model="dialog" scrollable max-width="1050px">
                <v-card>
                <v-card-title>
                    {{ formTitle }}
                    <v-spacer></v-spacer>
                    <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="Search"
                    single-line
                    hide-details
                    ></v-text-field>
                </v-card-title>
                <v-card-text style="height: 620px;">
                   
                    <v-form ref="form" v-model="valid" lazy-validation>
                        <div class="text-lg-center pa-5" style="width: 100%; margin-top:190px;" v-if="dataLoading">
                            <v-progress-circular width="7" size="70" indeterminate color="red">
                            </v-progress-circular>
                        </div>

                        <v-data-table
                        :headers="headers_workitems"
                        :items="results_workitems"
                        class="elevation-1"
                        :items-per-page="15"
                        dense
                        :search="search"
                        v-if="!dataLoading"
                        >   
                            <template v-slot:item.action="{ item }">
                                <v-icon
                                @click="addWorkItem(item, 'INFO')"
                                >
                                play_circle_filled
                                </v-icon>
                            </template>
                        </v-data-table>
                    </v-form>
                   
                </v-card-text>
            </v-card>
        </v-dialog>

        </v-container>
    </v-layout>
</template>

<script>
export default {
    data() {
        return {
            dialog: false,
            valid: true,
            headers: [
                { text: 'Queue ID', value: 'id' },
                { text: 'Queue Name', value: 'name' },
                { text: 'Status', value: 'status' },
                { text: 'CreatedOn', value: 'createdOn' },
                { text: 'UpdatedOn', value: 'updatedOn' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results: [],
            infoTitle: '',
            infoTaskName: '',
            infoTaskId: '',
            Structure_common: {
                task: '',
                param: '',
            },
            search: '',
            headers_workitems: [
                { text: 'ID', value: 'id' },
                { text: 'STATUS', value: 'status' },
                { text: 'RESULT', value: 'result' },
                { text: 'TASK', value: 'json.TASK' },
                { text: 'PARAM', value: 'json.PARAM' },
                { text: 'CreatedOn', value: 'createdOn' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results_workitems: [],
            dataLoading: true,
        }
    },
    mounted () {
        this.axios.get(this.$store.state.rOpsApi+'/wlm/queues').then(response => {
            this.results = response.data
        })
    },
    computed: {
        formTitle () {
            return this.infoTitle
        }
    },
    methods: {
        addWorkItem (item, type) {
            this.Structure_common.task = ''
            this.Structure_common.param = ''

            if (type == 'INFO') {
                this.Structure_common.task = this.infoTaskName;
                this.Structure_common.param = item.json.PARAM;
            } else {
                this.Structure_common.task = item.name;
                this.infoTaskId = item.id
            }
            confirm('Are you sure you want to add this queue?') &&
            this.axios.post(this.$store.state.rOpsApi+'/wlm/queues/'+this.infoTaskId+'/workitems',this.Structure_common).then(response => {
                if (response.data == "201 CREATED") {
                    alert("SUCCESS - (" + response.data + ")");

                    if (type == 'INFO')  this.infoItem_Info(this.infoTaskId)
                    
                } else {
                    alert("FAIL - (" + response.data + ")");
                }
            })
        },
        infoItem (item) {
             this.axios.post(this.$store.state.rOpsApi+'/wlm/queues/'+item.id+'/workitems/list').then(response => {
                this.results_workitems = response.data
                this.dataLoading = false
            })
            this.infoTitle = "Work items - " + item.name
            this.infoTaskName = item.name
            this.infoTaskId = item.id
            this.dialog = true
        },
        infoItem_Info (id) {
             this.axios.post(this.$store.state.rOpsApi+'/wlm/queues/'+id+'/workitems/list').then(response => {
                this.results_workitems = response.data
                this.dataLoading = false
            })
        },
        close () {
            this.dialog = false
            setTimeout(() => {
                this.dataLoading = true
            }, 300)
        },
    },
    watch: {
        dialog (val) {
            val || this.close()
            setTimeout(() => {
                this.dataLoading = true
            }, 300)
        }
    },
}
</script>

<style scoped>

</style>