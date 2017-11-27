<template>
    <div class="add-widget-wrapper">
        <div class="card">
            <div class="card-header">Widgets</div>

            <div v-if="widgets.length < 1" class="jumbotron mb-0">
                <h1 class="display-3">No Widgets!</h1>
                <p class="lead">Sorry currently there are no widgets. You can add a widget by clicking the <span class="badge badge-primary">Add Widget</span> button below.</p>
            </div>
            <ul v-else class="list-group widget-list">
                <draggable v-model="widgets" @end="sortWidgets">
                    <transition-group name="list-complete">
                        <li v-for="widget in widgets" :key="widget.id" class="list-group-item list-group-item-action">
                            <div class="d-flex justify-content-end w-100">
                                <div class="mr-auto p-2 align-middle">
                                    <i class="handdle material-icons mr-2">drag_handle</i>
                                    <span class="widget-decription">
                                    {{widget.description}}
                                    </span>
                                </div>
                                <div class="p-2">
                                    <div class="btn-group" role="group" aria-label="Basic example">
                                        <button class="btn btn-success" @click.prevent="editWidget(widget)">
                                            <i class="material-icons base-font-size">mode_edit</i>
                                        </button>
                                        <button class="btn btn-danger" @click.prevent="deleteWidget(widget)">
                                            <i class="material-icons base-font-size">delete</i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </transition-group>
                </draggable>
            </ul>

            <div class="card-footer text-right text-muted p-0">
                <a href="#" @click.prevent="modal.add = !modal.add" class="btn btn-primary mr-2 mt-2 mb-2"><i class="material-icons base-font-size">add</i> Add Widget</a>
            </div>
        </div>

        <p class="text-center text-white pt-3">Darg and drop to sort.</p>

        <widget-add v-if="modal.add" :widget-count="widgetCount" :modal="modal"></widget-add>
        <widget-edit v-if="modal.edit" :modal="modal"></widget-edit>
        <widget-delete v-if="modal.delete" :modal="modal"></widget-delete>

    </div>
</template>
<style>
.list-complete-item {
  padding: 4px;
  margin-top: 4px;
  border: solid 1px;
  transition: all 1s;
}

.list-complete-enter, .list-complete-leave-active {
  opacity: 0;
}
</style>
<script>
/**
* Widgets Index
*/
    import draggable from 'vuedraggable'
    export default{
        template: '<widgets></widgets>',
        //props: ['prop1','prop2'],
        data(){
            return{
                widgets: [],
                modal: {
                    add: false,
                    edit: false,
                    delete: false,
                    widget: {}
                }
            }
        },
        created() {
            let vm = this

            Bus.$on('addWidgetSuccess', function() {
                vm.fetchWidgets(); // Get Widgets
            }),
            Bus.$on('editWidgetSuccess', function() {
                vm.fetchWidgets(); // Get Widgets
            }),
            Bus.$on('deleteWidgetSuccess', function() {
                vm.fetchWidgets(); // Get Widgets
            })
        },
        mounted(){
            this.fetchWidgets();
        },
        computed:{
            widgetCount(){
                if( this.widgets.length ){
                    // insure adding a new widget is always larger ( deletion )
                    return this.widgets[this.widgets.length - 1].weight;
                }else{
                    return this.widgets.length;
                }
            }
        },
        watch:{

        },
        methods:{
            fetchWidgets() {
                console.log('Fetch Widgets');
                let vm = this

                let data = {};

                axios.post('/api/v1/widgets', data)
                .then( response => {
                    if(response.data.widgets.length > 0)
                    {
                        this.widgets = []
                        this.$nextTick(function () {
                            this.widgets.push.apply(this.widgets, response.data.widgets)
                        })
                        console.log('Fetch Widgets Complete')
                    }
                    else
                    {
                        this.widgets = []
                        console.log('No Widgets Found.')
                    }
                });
            },

            editWidget(widget){
                let vm = this;

                vm.modal.widget = widget;
                vm.modal.edit = true;
            },

            deleteWidget(widget){
                let vm = this;

                vm.modal.widget = widget;
                vm.modal.delete = true;
            },

            sortWidgets(){
                this.$nextTick(function () {
                    let vm = this;

                    const data = {
                        widgets : vm.widgets,
                    }

                    console.log('Sort Widgets');
                    console.log(data);

                    axios.post('/api/v1/widgets/sort', data)
                    .then( response => {
                        console.log('sort complete');
                    })
                    .catch( error => {
                        console.log('sort failure');
                    })
                })
            }
        },
        components:{
            draggable,
            'widget-add':                    require('./components/add.vue'),
            'widget-edit':                   require('./components/edit.vue'),
            'widget-delete':                 require('./components/delete.vue'),
        }
    }
</script>