<template>
    <v-layout row wrap>
        <v-container fluid>
             <v-row>
                <v-col
                cols="6"
                md="4"
                >
                    
                    <v-data-table
                    v-model="selected"
                    :headers="headers_codeType"
                    :items="results_codeType"
                    :single-select="true"
                    item-key="code_type_id"
                    show-select
                    class="elevation-1"
                    @item-selected="rowClickEvent_CodeType"
                    >   
                        <template v-slot:top>
                            <v-toolbar flat color="white">
                            <v-toolbar-title>RPA CodeType Table</v-toolbar-title>
                            <div class="flex-grow-1"></div>
                            <v-dialog v-model="dialog_codeType" max-width="500px">
                                <template v-slot:activator="{ on }">
                                    <v-btn class="ma-2" tile outlined color="success" v-on="on">
                                        <v-icon left>mdi-pencil</v-icon> New Code Type
                                    </v-btn>
                                </template>
                                <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle_codeType }}</span>
                                </v-card-title>
                    
                                <v-card-text>
                                    <v-container grid-list-md>
                                        <v-form ref="form_codeType" v-model="valid_codeType" lazy-validation>
                                            <v-layout row wrap>
                                                <v-flex xs3>
                                                    <v-text-field v-model="editedItem_codeType.code_type_id" label="CodeType ID" readonly disabled></v-text-field>
                                                </v-flex>
                                                <v-flex xs9>
                                                    <v-text-field v-model="editedItem_codeType.code_type_name" label="CodeType Name" :rules="[rules.required, rules.xss]"></v-text-field>
                                                </v-flex>
                                            </v-layout>
                                        </v-form>
                                    </v-container>
                                </v-card-text>
                    
                                <v-card-actions>
                                    <div class="flex-grow-1"></div>
                                    <v-btn color="teal darken-1" text @click="close_codeType">Cancel</v-btn>
                                    <v-btn color="teal darken-1" text @click="save_codeType">Save</v-btn>
                                </v-card-actions>
                                </v-card>
                            </v-dialog>
                            </v-toolbar>
                        </template>

                        <template v-slot:item.action="{ item }">
                            <v-icon
                            small
                            class="mr-2"
                            @click="editItem_codeType(item)"
                            >
                            edit
                            </v-icon>
                            <v-icon
                            small
                            @click="deleteItem_codeType(item)"
                            >
                            delete
                            </v-icon>
                        </template>
                    </v-data-table>
                </v-col>
    
                <v-col
                cols="12"
                md="8"
                >
                    <v-data-table
                    :headers="headers_code"
                    :items="results_code"
                    class="elevation-1"
                    >
                        <template v-slot:top>
                            <v-toolbar flat color="white">
                            <v-toolbar-title>RPA Code Table</v-toolbar-title>
                            <div class="flex-grow-1"></div>
                            <v-dialog v-model="dialog_code" max-width="500px">
                                <template v-slot:activator="{ on }">
                                    <v-btn class="ma-2"  id="btnNewCode" ref="btnNewCode" tile outlined color="success" v-on="on" :disabled="selected == 0" >
                                        <v-icon left>mdi-pencil</v-icon> New Code
                                    </v-btn>
                                </template>
                                <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle_code }}</span>
                                </v-card-title>
                    
                                <v-card-text>
                                    <v-container grid-list-md>
                                        <v-form ref="form_code" v-model="valid_code" lazy-validation>
                                            <v-layout row wrap>
                                                <v-flex xs3>
                                                    <v-text-field v-model="editedItem_code.code_type_id" label="CodeType ID" readonly disabled></v-text-field>
                                                </v-flex>
                                                <v-flex xs9>
                                                    <v-text-field v-model="editedItem_code.codeType.code_type_name" label="CodeType Name" readonly disabled></v-text-field>
                                                </v-flex>
                                                 <v-flex xs3>
                                                    <v-text-field v-model="editedItem_code.code_id" label="Code ID" readonly disabled></v-text-field>
                                                </v-flex>
                                                <v-flex xs9>
                                                    <v-text-field v-model="editedItem_code.code_name" label="Code Name" :rules="[rules.required, rules.xss]"></v-text-field>
                                                </v-flex>
                                            </v-layout>
                                        </v-form>
                                    </v-container>
                                </v-card-text>
                    
                                <v-card-actions>
                                    <div class="flex-grow-1"></div>
                                    <v-btn color="teal darken-1" text @click="close_code">Cancel</v-btn>
                                    <v-btn color="teal darken-1" text @click="save_code">Save</v-btn>
                                </v-card-actions>
                                </v-card>
                            </v-dialog>
                            </v-toolbar>
                        </template>
                        
                        <template v-slot:item.action="{ item }">
                            <v-icon
                            small
                            class="mr-2"
                            @click="editItem_code(item)"
                            >
                            edit
                            </v-icon>
                            <v-icon
                            small
                            @click="deleteItem_code(item)"
                            >
                            delete
                            </v-icon>
                        </template>
                    </v-data-table>
                </v-col>
             </v-row>
        </v-container>
    </v-layout>
</template>

<script>
export default {
    data() {
        return {
            selected: [],
            dialog_codeType: false,
            dialog_code: false,
            valid_codeType: true,
            valid_code: true,
            headers_codeType: [
                { text: 'CodeType ID', value: 'code_type_id' },
                { text: 'CodeType Name', value: 'code_type_name' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            headers_code: [
                { text: 'CodeType ID', value: 'codeType.code_type_id' },
                { text: 'CodeType Name', value: 'codeType.code_type_name' },
                { text: 'Code ID', value: 'code_id' },
                { text: 'Code Name', value: 'code_name' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results_codeType: [],
            results_code: [],
            editedIndex_codeType: -1,
            editedIndex_code: -1,
            editedItem_codeType: {
                code_type_id: '',
                code_type_name: ''
            },
            editedItem_code: {
                code_type_id: '',
                codeType:{code_type_name: ''},
                code_id: '',
                code_name: ''
            },
            defaultItem_codeType: {
                code_type_id: '',
                code_type_name: ''
            },
            defaultItem_code: {
                code_type_id: '',
                codeType:{code_type_name: ''},
                code_id: '',
                code_name: ''
            },
            rules: {
                required: value => !!value || '필수 입력 항목',
                xss: value => {
                    const pattern = /[<>"'&]/
                    return !pattern.test(value) || '사용금지 문자'
                },
            },
        }
    },
    mounted () {
        // Code Type Data
        this.axios.get(this.$store.state.rOpsApi+'/codeTypes').then(response => {
            this.results_codeType = response.data
        })
    },
    computed: {
        formTitle_codeType () {
            return this.editedIndex_codeType === -1 ? 'New Code Type' : 'Edit Code Type'
        },
        formTitle_code () {
            return this.editedIndex_code === -1 ? 'New Code' : 'Edit Code'
        }
    },
    watch: {
        dialog_codeType (val) {
            val || this.close_codeType()
            setTimeout(() => {
                this.resetValidation_codeType()
            }, 10)
        },

        dialog_code (val) {
            if (this.editedIndex_code == -1) {
                var data = this.selected[0]
                this.editedItem_code.code_type_id = data.code_type_id
                this.editedItem_code.codeType.code_type_name = data.code_type_name
            }

            val || this.close_code()
            setTimeout(() => {
                this.resetValidation_code()
            }, 10)
        }
    },
    methods: {
        // Code Type 관련 함수
        rowClickEvent_CodeType(evt) {
            if (evt.value == true) {
                // Code Data
                this.axios.get(this.$store.state.rOpsApi+'/codes/'+evt.item.code_type_id).then(response => {
                    this.results_code = response.data

                    if (this.results_code == '') {
                        this.$refs["btnNewCode"].abled 
                        this.results_code = []
                    }
                })
            } else {
                // Code Data
                this.results_code = [];
            }
        },

        editItem_codeType (item) {
            this.editedIndex_codeType = this.results_codeType.indexOf(item)
            this.editedItem_codeType = Object.assign({}, item)
            this.dialog_codeType = true
        },

        deleteItem_codeType (item) {
            const index = this.results_codeType.indexOf(item)
            confirm('Are you sure you want to delete this code type?') && this.results_codeType.splice(index, 1) && 
            this.axios.delete(this.$store.state.rOpsApi+'/admin/codeTypes/'+item.code_type_id)
        },

        close_codeType () {
            this.dialog_codeType = false
            setTimeout(() => {
                this.editedItem_codeType = Object.assign({}, this.defaultItem_codeType)
                this.editedIndex_codeType = -1
            }, 300)
        },

        save_codeType () {
            if (this.validate_codeType()) {
                if (this.editedIndex_codeType > -1) {
                    // 수정
                    this.axios.patch(this.$store.state.rOpsApi+'/admin/codeTypes/'+this.editedItem_codeType.code_type_id,this.editedItem_codeType).then(
                        Object.assign(this.results_codeType[this.editedIndex_codeType], this.editedItem_codeType),

                        // Code Data
                        this.axios.get(this.$store.state.rOpsApi+'/codes').then(response => {
                            this.results_code = response.data
                        })
                    )
                } else {
                    // 신규     
                    this.axios.post(this.$store.state.rOpsApi+'/admin/codeTypes',this.editedItem_codeType).then(
                        // Code Type Data
                        this.axios.get(this.$store.state.rOpsApi+'/codeTypes').then(response => {
                            this.results_codeType = response.data
                        })
                    )
                }
                this.close_codeType()
            }
        },

        validate_codeType() {
            return this.$refs.form_codeType.validate()
        },

        resetValidation_codeType() {
            this.$refs.form_codeType.resetValidation()
        },

        // Code 관련 함수
        editItem_code (item) {
            this.editedIndex_code = this.results_code.indexOf(item)
            this.editedItem_code = Object.assign({}, item)
            this.dialog_code = true
        },

        deleteItem_code (item) {
            const index = this.results_code.indexOf(item)
            confirm('Are you sure you want to delete this step?') && this.results_code.splice(index, 1) && 
            this.axios.delete(this.$store.state.rOpsApi+'/admin/codes/'+item.code_type_id+'/'+item.code_id)
        },

        close_code () {
            this.dialog_code = false
            setTimeout(() => {
                this.editedItem_code = Object.assign({}, this.defaultItem_code)
                this.editedIndex_code = -1
            }, 300)
        },

        save_code () {
            if (this.validate_code()) {
                if (this.editedIndex_code > -1) {
                    // 수정
                    this.axios.patch(this.$store.state.rOpsApi+'/admin/codes/'+this.editedItem_code.code_type_id+'/'+this.editedItem_code.code_id,this.editedItem_code).then(
                        Object.assign(this.results_code[this.editedIndex_code], this.editedItem_code)
                    )
                } else {
                    // 신규     
                    this.axios.post(this.$store.state.rOpsApi+'/admin/codes/'+this.editedItem_code.code_type_id,this.editedItem_code).then(response => {
                        this.results_code.push(response.data)
                    })
                }
                this.close_code()
            }
        },

        validate_code() {
            return this.$refs.form_code.validate()
        },

        resetValidation_code() {
            this.$refs.form_code.resetValidation()
        }
    }
}
</script>

<style scoped>

</style>