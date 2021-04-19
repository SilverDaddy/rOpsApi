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
                    <v-toolbar-title>RPA Step Table</v-toolbar-title>
                    <div class="flex-grow-1"></div>
                    <v-dialog v-model="dialog" max-width="500px">
                        <template v-slot:activator="{ on }">
                            <v-btn class="ma-2" tile outlined color="success" v-on="on">
                                <v-icon left>mdi-pencil</v-icon> New Step
                            </v-btn>
                            <v-flex xs12 sm3 d-flex style="margin-top:40px; margin-right:20px;"> 
                                <v-select 
                                    id="slJobs"
                                    ref="slJobs"
                                    :items="jobs"
                                    v-model="jobsSelected"
                                    item-text="job_name"
                                    item-value="job_id"
                                    label="Select Jobs"
                                    @change="changeJobs"
                                    style="width:100px; "
                                    dense
                                    :menu-props="{ maxHeight: '540' }"
                                >
                                </v-select>
                            </v-flex>
                        </template>
                        <v-card>
                        <v-card-title>
                            <span class="headline">{{ formTitle }}</span>
                        </v-card-title>
            
                        <v-card-text>
                            <v-container grid-list-md>
                                <v-form ref="form" v-model="valid" lazy-validation>
                                    <v-layout row wrap>
                                        <v-flex xs3>
                                            <v-text-field v-model="editedItem.job_id" label="Job ID" readonly></v-text-field>
                                        </v-flex>
                                        <v-flex xs9>
                                            <v-text-field v-model="editedItem.job.job_name" label="Job Name" readonly></v-text-field>
                                        </v-flex>
                                        <v-flex xs3>
                                            <v-text-field v-model="editedItem.step_id" label="Step ID" :rules="[rules.required, rules.xss]"></v-text-field>
                                        </v-flex>
                                        <v-flex xs9>
                                            <v-text-field v-model="editedItem.step_name" label="Step Name" :rules="[rules.required, rules.xss]"></v-text-field>
                                        </v-flex>
                                        <v-flex xs12>
                                            <v-text-field v-model="editedItem.description" label="Description" :rules="[rules.xss]"></v-text-field>
                                        </v-flex>
                                    </v-layout>
                                </v-form>
                            </v-container>
                        </v-card-text>
            
                        <v-card-actions>
                            <div class="flex-grow-1"></div>
                            <v-btn color="teal darken-1" text @click="close">Cancel</v-btn>
                            <v-btn color="teal darken-1" text @click="save">Save</v-btn>
                        </v-card-actions>
                        </v-card>
                    </v-dialog>
                    </v-toolbar>
                </template>
                
                <template v-slot:item.action="{ item }">
                    <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
                    >
                    edit
                    </v-icon>
                    <v-icon
                    small
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
            dialog: false,
            valid: true,
            headers: [
                { text: 'Job ID', value: 'job_id' },
                { text: 'Job Name', value: 'job.job_name' },
                { text: 'Step ID', value: 'step_id' },
                { text: 'Step Name', value: 'step_name' },
                { text: 'Description', value: 'description' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results: [],
            editedIndex: -1,
            editedItem: {
                job_id: '',
                step_id: '',
                step_name: '',
                description: '',
                job:{job_name: ''}
            },
            defaultItem: {
                job_id: '',
                step_id: '',
                step_name: '',
                description: '',
                job:{job_name: ''}
            },
            jobs: [],
            jobsSelected: [],
            selectedJobText: '',
            rules: {
                required: value => !!value || '필수 입력 항목',
                xss: value => {
                    const pattern = /[<>"'&]/
                    return !pattern.test(value) || '사용금지 문자'
                },
            }
        }
    },
    mounted () {
         // job 셀렉트 박스
        this.axios.get(this.$store.state.rOpsApi+'/jobs').then(response => {
            this.jobs = response.data
            this.jobsSelected = response.data[0]
            
            this.axios.get(this.$store.state.rOpsApi+'/steps/' + response.data[0].job_id).then(response => {
                this.results = response.data
            })
        })
    },
    computed: {
        formTitle () {
            return this.editedIndex === -1 ? 'New Step' : 'Edit Step'
        }
    },
    watch: {
        dialog (val) {
            var selected = this.$refs["slJobs"].selectedItems
            this.editedItem.job_id = selected[0].job_id
            this.editedItem.job.job_name = selected[0].job_name

            val || this.close()
            setTimeout(() => {
                this.resetValidation()
            }, 300)
        }
    },
    methods: {
        editItem (item) {
            this.editedIndex = this.results.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            const index = this.results.indexOf(item)
            confirm('Are you sure you want to delete this step?') && this.results.splice(index, 1) && 
            this.axios.delete(this.$store.state.rOpsApi+'/admin/steps/'+item.job_id+'/'+item.step_id)
        },

        close () {
            this.dialog = false
            setTimeout(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            }, 300)
        },

        save () {
            if (this.validate()) {
                if (this.editedIndex > -1) {
                    // 수정
                    this.axios.patch(this.$store.state.rOpsApi+'/admin/steps/'+this.editedItem.job_id+'/'+this.editedItem.step_id,this.editedItem).then(
                        Object.assign(this.results[this.editedIndex], this.editedItem)
                    )
                } else {
                    // 신규     
                    this.axios.post(this.$store.state.rOpsApi+'/admin/steps/'+this.editedItem.job_id,this.editedItem).then(
                        this.results.push(this.editedItem)
                    )
                }
                this.close()
            }
        },

        changeJobs(slJobId) {
            this.axios.get(this.$store.state.rOpsApi+'/steps/' + slJobId).then(response => {
                this.results = response.data
            })
        },

        validate() {
            return this.$refs.form.validate()
        },

        resetValidation() {
            this.$refs.form.resetValidation()
        },
    }
}
</script>

<style scoped>

</style>