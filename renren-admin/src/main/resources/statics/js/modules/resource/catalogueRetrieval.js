$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            resourceTitle:'',
            resourceSign:'',
            keyword:'',
            metedataIdentifier:'',
            reviewState:'',
        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteType:null,
            categoryId:null,
            categoryName:'',
            catagoryCode:'',
            catalogId:'',
            catalogName:''
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        h:0,
        props: {
            label: 'name',
            children: 'list',
        },
        count: 1,
        id:0,
        filterText:'',
        menuList:[],
        tableList:[],
        totalPage:0,
        page:1,
        pageSize:10,
        tab:0,
        catalogId:null,
        headerList:[],
        loading:false
    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        }
    },
    methods: {
        // 收缩展开搜索
        openSwitch:function () {
            if(vm.open){
                vm.open = false;
                vm.openText = '收起筛选'

            }else {
                vm.open = true;
                vm.openText = '展开筛选'
            }
        },
        filterNode:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 获取树目录列表
        getMenuList: function (event) {
            $.getJSON(baseURL + "xj/xjcatalog/myList", function(r){
                vm.menuList = [];
                var _len=0;
                for(var i = 1;i<10;i++){
                    if(i == 1){
                        if(_len == r.length){
                            break ;
                        }
                        r.forEach(function (item) {
                            if(item.parentId == 0){
                                vm.menuList.push({
                                    name:item.catalogName,
                                    id:item.catalogId,
                                    list:[]
                                })
                                _len++;
                            }
                        })
                    }else if(i == 2){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            r.forEach(function (n) {
                                if(n.parentId == item.id){
                                    item.list.push({
                                        name:n.catalogName,
                                        id:n.catalogId,
                                        list:[]
                                    })
                                    _len++;
                                }
                            })
                        })
                    }else if(i == 3){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                r.forEach(function (n) {
                                    if(n.parentId == i.id){
                                        i.list.push({
                                            name:n.catalogName,
                                            id:n.catalogId,
                                            list:[]
                                        })
                                    }
                                    _len++;
                                })
                            })

                        })
                    }else if(i == 4){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    r.forEach(function (n) {
                                        if(n.parentId == j.id){
                                            j.list.push({
                                                name:n.catalogName,
                                                id:n.catalogId,
                                                list:[]
                                            })
                                        }
                                        _len++;
                                    })
                                })
                            })

                        })
                    }else if(i == 5){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        r.forEach(function (n) {
                                            if(n.parentId == m.id){
                                                m.list.push({
                                                    name:n.catalogName,
                                                    id:n.catalogId,
                                                    list:[]
                                                })
                                            }
                                            _len++;
                                        })
                                    })
                                })
                            })

                        })
                    }else if(i == 6){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        m.list.forEach(function (x) {
                                            r.forEach(function (n) {
                                                if(n.parentId == x.id){
                                                    x.list.push({
                                                        name:n.catalogName,
                                                        id:n.catalogId,
                                                        list:[]
                                                    })
                                                }
                                                _len++;
                                            })
                                        })
                                    })
                                })
                            })

                        })
                    }

                }
                var _list = [{
                    name:'资源目录',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.menuList;
                vm.menuList = _list;
            });
        },
        // 获取表格列表
        getTableList:function () {
            this.loading = true;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjcatalog/selectDataList',
                // contentType: "application/json",resource/resourcemetedata/list2
                dataType: 'json',
                data: {
                    page:this.page,
                    catalogId:this.catalogId,
                },
                success: function(r){
                    if(r.code === 0){
                        vm.loading = false;
                        vm.tableList = r.page.dataList;
                        vm.totalPage = r.page.totalCount;
                        vm.headerList = r.page.headerList
                    }else{
                        vm.loading = false;
                        alert(r.msg);
                    }
                }
            });
        },

        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getTableList();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            if(data.id){
                vm.catalogId = data.id;
                vm.getTableList();
            }
        },
        // 选项卡
        tabClick:function (num) {
            vm.tab = num;
            vm.page = 1;
            vm.getTableList();
        },
        // 重置
        clean:function () {
            vm.q =  {
                resourceTitle:'',
                resourceSign:'',
                keyword:'',
                metedataIdentifier:'',
                reviewState:'',
            };
        },
        // 查询
        look:function () {
            vm.page = 1;
            vm.getTableList();
        }
    },
    created:function () {
        this.getMenuList();
        // this.getTableList();

        // this.h = height
    }
});


