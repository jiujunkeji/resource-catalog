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
        menuList1:[],
        tableList:[],
        totalPage:0,
        page:1,
        pageSize:10,
        tab:0,
        catalogId:null,
        headerList:[],
        loading:false,
        meteCategory:{},
        dataSourceList:[]
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
                                    type:item.datasourceType,
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
                                        type:n.datasourceType,
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
                                            type:n.datasourceType,
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
                                                type:n.datasourceType,
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
                                                    type:n.datasourceType,
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
                                                        type:n.datasourceType,
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
        // 设置树目录结构是否有下级
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjcatalog/list", function(r){
                vm.menuList1 = [];
                var _len=0;
                var arr = [];
                for(var i = 1;i<10;i++){
                    if(i == 1){
                        if(_len == r.length){
                            break ;
                        }
                        r.forEach(function (item) {
                            if(item.parentId == 0){
                                vm.menuList1.push({
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
                        vm.menuList1.forEach(function (item) {
                            r.forEach(function (n) {
                                if(n.parentId == item.id){
                                    arr.push(item.id);
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
                        vm.menuList1.forEach(function (item) {
                            item.list.forEach(function (i) {
                                r.forEach(function (n) {
                                    if(n.parentId == i.id){
                                        arr.push(i.id);
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
                        vm.menuList1.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    r.forEach(function (n) {
                                        if(n.parentId == j.id){
                                            arr.push(i.id);
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
                        vm.menuList1.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        r.forEach(function (n) {
                                            if(n.parentId == m.id){
                                                arr.push(i.id);
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
                        vm.menuList1.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        m.list.forEach(function (x) {
                                            r.forEach(function (n) {
                                                if(n.parentId == x.id){
                                                    arr.push(i.id);
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
                vm.isTwoArr = newArr(arr);
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
                if(vm.isTwoArr.indexOf(data.id) == -1){
                    if(JSON.stringify(data.type) == 'null'){
                        this.$message({
                            message: '该目录未关联数据',
                            type: 'warning'
                        });
                    }else if(data.type == 1){
                        vm.catalogId = data.id;
                        vm.getTableList();
                    }else {
                        $.ajax({
                            type: "get",
                            url: baseURL + 'xj/xjcataloglinkdata/getDataSource',
                            // contentType: "application/json",
                            dataType: 'json',
                            data: {
                                catalogId:data.id
                            },
                            success: function(r){
                                if(r.code === 0){
                                    vm.addKettle(r.linkData);
                                }else{
                                    alert(r.msg);
                                }
                            }
                        });

                    }

                }

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
        },
        // 新增任务
        addKettle:function (obj) {
            var that = this;
            vm.meteCategory = {
                ktrName:obj.catalogName+'-抽取',
                ktrDsname:'',
                ktrTablename:obj.tableName,
                ktrDsid:obj.dataSourceId,
                ktrDsname:obj.dsName
            }
            layer.open({
                type: 1,
                title: '新增任务',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '460px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    if(vm.meteCategory.ktrTablename == ''){
                        that.$message({
                            message: "带 ' * ' 的为必填项",
                            type: 'warning'
                        });
                    }else {
                        const loading = that.$loading({
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        $.ajax({
                            type: "POST",
                            url: baseURL + 'xj/xjktr/save',
                            contentType: "application/json",
                            data: JSON.stringify(vm.meteCategory),
                            success: function(r){
                                if(r.code === 0){
                                    vm.reload();
                                    loading.close();
                                    layer.close(index);
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功<br>可以去数据抽取目录下查看相应任务</div>',{skin:'bg-class',area: ['400px', '270px'],});
                                }else{
                                    loading.close()
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                                }
                            }
                        });
                    }
                },
                btn2:function () {
                }

            })
        },
        // 获取数据源表格列表
        getShujuyuanList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjdatasource/list2',
                // contentType: "application/json",
                dataType: 'json',
                success: function(r){
                    if(r.code === 0){
                        vm.dataSourceList = r.list;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 数据源改变
        dsChange:function (opt) {
            vm.dataSourceList.forEach(function (item) {
                if(item.dsId == opt){
                    vm.meteCategory.ktrDsname = item.dsName
                }
            })
        },
    },
    created:function () {
        this.getMenuList();
        this.getMenuList1();
        this.getShujuyuanList();

        // this.h = height
    }
});
function newArr(arr){
    return Array.from(new Set(arr))
}


