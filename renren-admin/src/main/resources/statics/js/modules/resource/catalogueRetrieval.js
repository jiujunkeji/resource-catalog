$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            name:''
        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteType:null,
            categoryId:null,
            categoryName:'',
            categoryCode:'',
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
        catalogId:null
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
            $.getJSON(baseURL + "resource/resourcecatalog/list", function(r){
                var _len=0;
                for(var i = 1;i<100;i++){
                    if(i == 1){
                        if(_len == r.length){
                            return ;
                        }
                        r.forEach(function (item) {
                            if(item.parentId == 0){
                                vm.menuList.push({
                                    name:item.name,
                                    id:item.catalogId,
                                    list:[]
                                })
                                _len++;
                            }
                        })
                    }else if(i == 2){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            r.forEach(function (n) {
                                if(n.parentId == item.id){
                                    item.list.push({
                                        name:n.name,
                                        id:n.catalogId,
                                        list:[]
                                    })
                                    _len++;
                                }
                            })
                        })
                    }else if(i == 3){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                r.forEach(function (n) {
                                    if(n.parentId == i.id){
                                        i.list.push({
                                            name:n.name,
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
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    r.forEach(function (n) {
                                        if(n.parentId == j.id){
                                            j.list.push({
                                                name:n.name,
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
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        r.forEach(function (n) {
                                            if(n.parentId == m.id){
                                                m.list.push({
                                                    name:n.name,
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
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        m.list.forEach(function (x) {
                                            r.forEach(function (n) {
                                                if(n.parentId == x.id){
                                                    x.list.push({
                                                        name:n.name,
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
                    name:'全部',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.menuList;

                vm.menuList = _list;
                console.log(vm.menuList);
            });
        },
        // 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/list1',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    catalogId:this.catalogId,
                    type:this.tab
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalPage;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },

        // 分页
        layerPage:function (currentPage) {
            console.log(currentPage);
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            console.log(data);
            if(data.list.length == 0 || data.id == null){
                console.log('进来了')
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
        // 表格选中方法
        toggleSelection:function(selection) {
            console.log(selection);
            vm.checkIdList = selection;
        },
        // 通过方法
        agree:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/agree',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.tab = 1;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 拒绝
        refuse:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/refuse',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    if(r.code === 0){
                        // vm.tab = 1;
                        // vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        alert(r.msg);
                    }
                }
            });

        },
        // 发布
        push:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/push',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 2;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 停止发布
        stopPush:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/stopPush',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 1;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        clean:function () {
            vm.q.name = null
        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();

        // this.h = height
    }
});


