(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-38cde64f"],{"169a":function(e,t,i){"use strict";i("7db0"),i("caad"),i("45fc"),i("a9e3"),i("2532"),i("498a");var o=i("5530"),d=i("2909"),s=i("ade3"),n=(i("368e"),i("480e")),a=i("4ad4"),c=i("b848"),r=i("75eb"),l=i("e707"),u=i("e4d3"),h=i("21be"),_=i("f2e7"),p=i("a293"),f=i("58df"),v=i("d9bd"),m=i("80d2"),y=Object(f["a"])(a["a"],c["a"],r["a"],l["a"],u["a"],h["a"],_["a"]);t["a"]=y.extend({name:"v-dialog",directives:{ClickOutside:p["a"]},props:{dark:Boolean,disabled:Boolean,fullscreen:Boolean,light:Boolean,maxWidth:{type:[String,Number],default:"none"},noClickAnimation:Boolean,origin:{type:String,default:"center center"},persistent:Boolean,retainFocus:{type:Boolean,default:!0},scrollable:Boolean,transition:{type:[String,Boolean],default:"dialog-transition"},width:{type:[String,Number],default:"auto"}},data:function(){return{activatedBy:null,animate:!1,animateTimeout:-1,isActive:!!this.value,stackMinZIndex:200,previousActiveElement:null}},computed:{classes:function(){var e;return e={},Object(s["a"])(e,"v-dialog ".concat(this.contentClass).trim(),!0),Object(s["a"])(e,"v-dialog--active",this.isActive),Object(s["a"])(e,"v-dialog--persistent",this.persistent),Object(s["a"])(e,"v-dialog--fullscreen",this.fullscreen),Object(s["a"])(e,"v-dialog--scrollable",this.scrollable),Object(s["a"])(e,"v-dialog--animated",this.animate),e},contentClasses:function(){return{"v-dialog__content":!0,"v-dialog__content--active":this.isActive}},hasActivator:function(){return Boolean(!!this.$slots.activator||!!this.$scopedSlots.activator)}},watch:{isActive:function(e){var t;e?(this.show(),this.hideScroll()):(this.removeOverlay(),this.unbind(),null==(t=this.previousActiveElement)||t.focus())},fullscreen:function(e){this.isActive&&(e?(this.hideScroll(),this.removeOverlay(!1)):(this.showScroll(),this.genOverlay()))}},created:function(){this.$attrs.hasOwnProperty("full-width")&&Object(v["e"])("full-width",this)},beforeMount:function(){var e=this;this.$nextTick((function(){e.isBooted=e.isActive,e.isActive&&e.show()}))},beforeDestroy:function(){"undefined"!==typeof window&&this.unbind()},methods:{animateClick:function(){var e=this;this.animate=!1,this.$nextTick((function(){e.animate=!0,window.clearTimeout(e.animateTimeout),e.animateTimeout=window.setTimeout((function(){return e.animate=!1}),150)}))},closeConditional:function(e){var t=e.target;return!(this._isDestroyed||!this.isActive||this.$refs.content.contains(t)||this.overlay&&t&&!this.overlay.$el.contains(t))&&this.activeZIndex>=this.getMaxZIndex()},hideScroll:function(){this.fullscreen?document.documentElement.classList.add("overflow-y-hidden"):l["a"].options.methods.hideScroll.call(this)},show:function(){var e=this;!this.fullscreen&&!this.hideOverlay&&this.genOverlay(),this.$nextTick((function(){e.$nextTick((function(){e.previousActiveElement=document.activeElement,e.$refs.content.focus(),e.bind()}))}))},bind:function(){window.addEventListener("focusin",this.onFocusin)},unbind:function(){window.removeEventListener("focusin",this.onFocusin)},onClickOutside:function(e){this.$emit("click:outside",e),this.persistent?this.noClickAnimation||this.animateClick():this.isActive=!1},onKeydown:function(e){if(e.keyCode===m["x"].esc&&!this.getOpenDependents().length)if(this.persistent)this.noClickAnimation||this.animateClick();else{this.isActive=!1;var t=this.getActivator();this.$nextTick((function(){return t&&t.focus()}))}this.$emit("keydown",e)},onFocusin:function(e){if(e&&this.retainFocus){var t=e.target;if(t&&![document,this.$refs.content].includes(t)&&!this.$refs.content.contains(t)&&this.activeZIndex>=this.getMaxZIndex()&&!this.getOpenDependentElements().some((function(e){return e.contains(t)}))){var i=this.$refs.content.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'),o=Object(d["a"])(i).find((function(e){return!e.hasAttribute("disabled")}));o&&o.focus()}}},genContent:function(){var e=this;return this.showLazyContent((function(){return[e.$createElement(n["a"],{props:{root:!0,light:e.light,dark:e.dark}},[e.$createElement("div",{class:e.contentClasses,attrs:Object(o["a"])({role:"document",tabindex:e.isActive?0:void 0},e.getScopeIdAttrs()),on:{keydown:e.onKeydown},style:{zIndex:e.activeZIndex},ref:"content"},[e.genTransition()])])]}))},genTransition:function(){var e=this.genInnerContent();return this.transition?this.$createElement("transition",{props:{name:this.transition,origin:this.origin,appear:!0}},[e]):e},genInnerContent:function(){var e={class:this.classes,ref:"dialog",directives:[{name:"click-outside",value:{handler:this.onClickOutside,closeConditional:this.closeConditional,include:this.getOpenDependentElements}},{name:"show",value:this.isActive}],style:{transformOrigin:this.origin}};return this.fullscreen||(e.style=Object(o["a"])(Object(o["a"])({},e.style),{},{maxWidth:"none"===this.maxWidth?void 0:Object(m["g"])(this.maxWidth),width:"auto"===this.width?void 0:Object(m["g"])(this.width)})),this.$createElement("div",e,this.getContentSlot())}},render:function(e){return e("div",{staticClass:"v-dialog__container",class:{"v-dialog__container--attached":""===this.attach||!0===this.attach||"attach"===this.attach},attrs:{role:"dialog"}},[this.genActivator(),this.genContent()])}})},"368e":function(e,t,i){},"4bd4":function(e,t,i){"use strict";i("4de4"),i("7db0"),i("4160"),i("caad"),i("07ac"),i("2532"),i("159b");var o=i("5530"),d=i("58df"),s=i("7e2b"),n=i("3206");t["a"]=Object(d["a"])(s["a"],Object(n["b"])("form")).extend({name:"v-form",provide:function(){return{form:this}},inheritAttrs:!1,props:{disabled:Boolean,lazyValidation:Boolean,readonly:Boolean,value:Boolean},data:function(){return{inputs:[],watchers:[],errorBag:{}}},watch:{errorBag:{handler:function(e){var t=Object.values(e).includes(!0);this.$emit("input",!t)},deep:!0,immediate:!0}},methods:{watchInput:function(e){var t=this,i=function(e){return e.$watch("hasError",(function(i){t.$set(t.errorBag,e._uid,i)}),{immediate:!0})},o={_uid:e._uid,valid:function(){},shouldValidate:function(){}};return this.lazyValidation?o.shouldValidate=e.$watch("shouldValidate",(function(d){d&&(t.errorBag.hasOwnProperty(e._uid)||(o.valid=i(e)))})):o.valid=i(e),o},validate:function(){return 0===this.inputs.filter((function(e){return!e.validate(!0)})).length},reset:function(){this.inputs.forEach((function(e){return e.reset()})),this.resetErrorBag()},resetErrorBag:function(){var e=this;this.lazyValidation&&setTimeout((function(){e.errorBag={}}),0)},resetValidation:function(){this.inputs.forEach((function(e){return e.resetValidation()})),this.resetErrorBag()},register:function(e){this.inputs.push(e),this.watchers.push(this.watchInput(e))},unregister:function(e){var t=this.inputs.find((function(t){return t._uid===e._uid}));if(t){var i=this.watchers.find((function(e){return e._uid===t._uid}));i&&(i.valid(),i.shouldValidate()),this.watchers=this.watchers.filter((function(e){return e._uid!==t._uid})),this.inputs=this.inputs.filter((function(e){return e._uid!==t._uid})),this.$delete(this.errorBag,t._uid)}}},render:function(e){var t=this;return e("form",{staticClass:"v-form",attrs:Object(o["a"])({novalidate:!0},this.attrs$),on:{submit:function(e){return t.$emit("submit",e)}}},this.$slots.default)}})},"5fd9":function(e,t,i){"use strict";i.r(t);var o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-layout",{attrs:{row:"",wrap:""}},[i("v-container",{attrs:{fluid:""}},[i("v-row",[i("v-col",{attrs:{cols:"6",md:"4"}},[i("v-data-table",{staticClass:"elevation-1",attrs:{headers:e.headers_codeType,items:e.results_codeType,"single-select":!0,"item-key":"code_type_id","show-select":""},on:{"item-selected":e.rowClickEvent_CodeType},scopedSlots:e._u([{key:"top",fn:function(){return[i("v-toolbar",{attrs:{flat:"",color:"white"}},[i("v-toolbar-title",[e._v("RPA CodeType Table")]),i("div",{staticClass:"flex-grow-1"}),i("v-dialog",{attrs:{"max-width":"500px"},scopedSlots:e._u([{key:"activator",fn:function(t){var o=t.on;return[i("v-btn",e._g({staticClass:"ma-2",attrs:{tile:"",outlined:"",color:"success"}},o),[i("v-icon",{attrs:{left:""}},[e._v("mdi-pencil")]),e._v(" New Code Type ")],1)]}}]),model:{value:e.dialog_codeType,callback:function(t){e.dialog_codeType=t},expression:"dialog_codeType"}},[i("v-card",[i("v-card-title",[i("span",{staticClass:"headline"},[e._v(e._s(e.formTitle_codeType))])]),i("v-card-text",[i("v-container",{attrs:{"grid-list-md":""}},[i("v-form",{ref:"form_codeType",attrs:{"lazy-validation":""},model:{value:e.valid_codeType,callback:function(t){e.valid_codeType=t},expression:"valid_codeType"}},[i("v-layout",{attrs:{row:"",wrap:""}},[i("v-flex",{attrs:{xs3:""}},[i("v-text-field",{attrs:{label:"CodeType ID",readonly:"",disabled:""},model:{value:e.editedItem_codeType.code_type_id,callback:function(t){e.$set(e.editedItem_codeType,"code_type_id",t)},expression:"editedItem_codeType.code_type_id"}})],1),i("v-flex",{attrs:{xs9:""}},[i("v-text-field",{attrs:{label:"CodeType Name",rules:[e.rules.required,e.rules.xss]},model:{value:e.editedItem_codeType.code_type_name,callback:function(t){e.$set(e.editedItem_codeType,"code_type_name",t)},expression:"editedItem_codeType.code_type_name"}})],1)],1)],1)],1)],1),i("v-card-actions",[i("div",{staticClass:"flex-grow-1"}),i("v-btn",{attrs:{color:"teal darken-1",text:""},on:{click:e.close_codeType}},[e._v("Cancel")]),i("v-btn",{attrs:{color:"teal darken-1",text:""},on:{click:e.save_codeType}},[e._v("Save")])],1)],1)],1)],1)]},proxy:!0},{key:"item.action",fn:function(t){var o=t.item;return[i("v-icon",{staticClass:"mr-2",attrs:{small:""},on:{click:function(t){return e.editItem_codeType(o)}}},[e._v(" edit ")]),i("v-icon",{attrs:{small:""},on:{click:function(t){return e.deleteItem_codeType(o)}}},[e._v(" delete ")])]}}]),model:{value:e.selected,callback:function(t){e.selected=t},expression:"selected"}})],1),i("v-col",{attrs:{cols:"12",md:"8"}},[i("v-data-table",{staticClass:"elevation-1",attrs:{headers:e.headers_code,items:e.results_code},scopedSlots:e._u([{key:"top",fn:function(){return[i("v-toolbar",{attrs:{flat:"",color:"white"}},[i("v-toolbar-title",[e._v("RPA Code Table")]),i("div",{staticClass:"flex-grow-1"}),i("v-dialog",{attrs:{"max-width":"500px"},scopedSlots:e._u([{key:"activator",fn:function(t){var o=t.on;return[i("v-btn",e._g({ref:"btnNewCode",staticClass:"ma-2",attrs:{id:"btnNewCode",tile:"",outlined:"",color:"success",disabled:0==e.selected}},o),[i("v-icon",{attrs:{left:""}},[e._v("mdi-pencil")]),e._v(" New Code ")],1)]}}]),model:{value:e.dialog_code,callback:function(t){e.dialog_code=t},expression:"dialog_code"}},[i("v-card",[i("v-card-title",[i("span",{staticClass:"headline"},[e._v(e._s(e.formTitle_code))])]),i("v-card-text",[i("v-container",{attrs:{"grid-list-md":""}},[i("v-form",{ref:"form_code",attrs:{"lazy-validation":""},model:{value:e.valid_code,callback:function(t){e.valid_code=t},expression:"valid_code"}},[i("v-layout",{attrs:{row:"",wrap:""}},[i("v-flex",{attrs:{xs3:""}},[i("v-text-field",{attrs:{label:"CodeType ID",readonly:"",disabled:""},model:{value:e.editedItem_code.code_type_id,callback:function(t){e.$set(e.editedItem_code,"code_type_id",t)},expression:"editedItem_code.code_type_id"}})],1),i("v-flex",{attrs:{xs9:""}},[i("v-text-field",{attrs:{label:"CodeType Name",readonly:"",disabled:""},model:{value:e.editedItem_code.codeType.code_type_name,callback:function(t){e.$set(e.editedItem_code.codeType,"code_type_name",t)},expression:"editedItem_code.codeType.code_type_name"}})],1),i("v-flex",{attrs:{xs3:""}},[i("v-text-field",{attrs:{label:"Code ID",readonly:"",disabled:""},model:{value:e.editedItem_code.code_id,callback:function(t){e.$set(e.editedItem_code,"code_id",t)},expression:"editedItem_code.code_id"}})],1),i("v-flex",{attrs:{xs9:""}},[i("v-text-field",{attrs:{label:"Code Name",rules:[e.rules.required,e.rules.xss]},model:{value:e.editedItem_code.code_name,callback:function(t){e.$set(e.editedItem_code,"code_name",t)},expression:"editedItem_code.code_name"}})],1)],1)],1)],1)],1),i("v-card-actions",[i("div",{staticClass:"flex-grow-1"}),i("v-btn",{attrs:{color:"teal darken-1",text:""},on:{click:e.close_code}},[e._v("Cancel")]),i("v-btn",{attrs:{color:"teal darken-1",text:""},on:{click:e.save_code}},[e._v("Save")])],1)],1)],1)],1)]},proxy:!0},{key:"item.action",fn:function(t){var o=t.item;return[i("v-icon",{staticClass:"mr-2",attrs:{small:""},on:{click:function(t){return e.editItem_code(o)}}},[e._v(" edit ")]),i("v-icon",{attrs:{small:""},on:{click:function(t){return e.deleteItem_code(o)}}},[e._v(" delete ")])]}}])})],1)],1)],1)],1)},d=[],s=(i("c975"),i("a434"),{data:function(){return{selected:[],dialog_codeType:!1,dialog_code:!1,valid_codeType:!0,valid_code:!0,headers_codeType:[{text:"CodeType ID",value:"code_type_id"},{text:"CodeType Name",value:"code_type_name"},{text:"Actions",value:"action",sortable:!1}],headers_code:[{text:"CodeType ID",value:"codeType.code_type_id"},{text:"CodeType Name",value:"codeType.code_type_name"},{text:"Code ID",value:"code_id"},{text:"Code Name",value:"code_name"},{text:"Actions",value:"action",sortable:!1}],results_codeType:[],results_code:[],editedIndex_codeType:-1,editedIndex_code:-1,editedItem_codeType:{code_type_id:"",code_type_name:""},editedItem_code:{code_type_id:"",codeType:{code_type_name:""},code_id:"",code_name:""},defaultItem_codeType:{code_type_id:"",code_type_name:""},defaultItem_code:{code_type_id:"",codeType:{code_type_name:""},code_id:"",code_name:""},rules:{required:function(e){return!!e||"필수 입력 항목"},xss:function(e){var t=/[<>"'&]/;return!t.test(e)||"사용금지 문자"}}}},mounted:function(){var e=this;this.axios.get(this.$store.state.rOpsApi+"/codeTypes").then((function(t){e.results_codeType=t.data}))},computed:{formTitle_codeType:function(){return-1===this.editedIndex_codeType?"New Code Type":"Edit Code Type"},formTitle_code:function(){return-1===this.editedIndex_code?"New Code":"Edit Code"}},watch:{dialog_codeType:function(e){var t=this;e||this.close_codeType(),setTimeout((function(){t.resetValidation_codeType()}),10)},dialog_code:function(e){var t=this;if(-1==this.editedIndex_code){var i=this.selected[0];this.editedItem_code.code_type_id=i.code_type_id,this.editedItem_code.codeType.code_type_name=i.code_type_name}e||this.close_code(),setTimeout((function(){t.resetValidation_code()}),10)}},methods:{rowClickEvent_CodeType:function(e){var t=this;1==e.value?this.axios.get(this.$store.state.rOpsApi+"/codes/"+e.item.code_type_id).then((function(e){t.results_code=e.data,""==t.results_code&&(t.$refs["btnNewCode"].abled,t.results_code=[])})):this.results_code=[]},editItem_codeType:function(e){this.editedIndex_codeType=this.results_codeType.indexOf(e),this.editedItem_codeType=Object.assign({},e),this.dialog_codeType=!0},deleteItem_codeType:function(e){var t=this.results_codeType.indexOf(e);confirm("Are you sure you want to delete this code type?")&&this.results_codeType.splice(t,1)&&this.axios.delete(this.$store.state.rOpsApi+"/admin/codeTypes/"+e.code_type_id)},close_codeType:function(){var e=this;this.dialog_codeType=!1,setTimeout((function(){e.editedItem_codeType=Object.assign({},e.defaultItem_codeType),e.editedIndex_codeType=-1}),300)},save_codeType:function(){var e=this;this.validate_codeType()&&(this.editedIndex_codeType>-1?this.axios.patch(this.$store.state.rOpsApi+"/admin/codeTypes/"+this.editedItem_codeType.code_type_id,this.editedItem_codeType).then(Object.assign(this.results_codeType[this.editedIndex_codeType],this.editedItem_codeType),this.axios.get(this.$store.state.rOpsApi+"/codes").then((function(t){e.results_code=t.data}))):this.axios.post(this.$store.state.rOpsApi+"/admin/codeTypes",this.editedItem_codeType).then(this.axios.get(this.$store.state.rOpsApi+"/codeTypes").then((function(t){e.results_codeType=t.data}))),this.close_codeType())},validate_codeType:function(){return this.$refs.form_codeType.validate()},resetValidation_codeType:function(){this.$refs.form_codeType.resetValidation()},editItem_code:function(e){this.editedIndex_code=this.results_code.indexOf(e),this.editedItem_code=Object.assign({},e),this.dialog_code=!0},deleteItem_code:function(e){var t=this.results_code.indexOf(e);confirm("Are you sure you want to delete this step?")&&this.results_code.splice(t,1)&&this.axios.delete(this.$store.state.rOpsApi+"/admin/codes/"+e.code_type_id+"/"+e.code_id)},close_code:function(){var e=this;this.dialog_code=!1,setTimeout((function(){e.editedItem_code=Object.assign({},e.defaultItem_code),e.editedIndex_code=-1}),300)},save_code:function(){var e=this;this.validate_code()&&(this.editedIndex_code>-1?this.axios.patch(this.$store.state.rOpsApi+"/admin/codes/"+this.editedItem_code.code_type_id+"/"+this.editedItem_code.code_id,this.editedItem_code).then(Object.assign(this.results_code[this.editedIndex_code],this.editedItem_code)):this.axios.post(this.$store.state.rOpsApi+"/admin/codes/"+this.editedItem_code.code_type_id,this.editedItem_code).then((function(t){e.results_code.push(t.data)})),this.close_code())},validate_code:function(){return this.$refs.form_code.validate()},resetValidation_code:function(){this.$refs.form_code.resetValidation()}}}),n=s,a=i("2877"),c=i("6544"),r=i.n(c),l=i("8336"),u=i("b0af"),h=i("99d9"),_=i("62ad"),p=i("a523"),f=i("8fea"),v=i("169a"),m=i("0e8f"),y=i("4bd4"),T=i("132d"),x=i("a722"),b=i("0fd9"),g=i("8654"),I=i("71d9"),w=i("2a7f"),C=Object(a["a"])(n,o,d,!1,null,"83f97864",null);t["default"]=C.exports;r()(C,{VBtn:l["a"],VCard:u["a"],VCardActions:h["a"],VCardText:h["b"],VCardTitle:h["c"],VCol:_["a"],VContainer:p["a"],VDataTable:f["a"],VDialog:v["a"],VFlex:m["a"],VForm:y["a"],VIcon:T["a"],VLayout:x["a"],VRow:b["a"],VTextField:g["a"],VToolbar:I["a"],VToolbarTitle:w["a"]})}}]);
//# sourceMappingURL=chunk-38cde64f.aa7dd628.js.map