$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "catalogId",
            pIdKey: "parentId",
            rootPId: -1,

        },
        key: {
            url:"nourl",
            name:'catalogName'
        }
    }
};
var ztree;

var setting1 = {
    data: {
        simpleData: {
            enable: true,
            idKey: "meteCategoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree1;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q1: {
            cnName:'',
            meteSetNumber:'',
            meteCategorySetId:'',

        },
        nameS:'',
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
        open1:true,
        openText:'展开筛选',
        openText1:'展开筛选',
        h:0,
        h1:0,
        props: {
            label: 'name',
            children: 'list',
        },
        count: 1,
        id:0,
        filterText:'',
        filterText1:'',
        menuList:[],
        menuList1:[],
        tableList:[],
        tableList1:[],
        totalPage:0,
        totalPage1:0,
        page:1,
        page1:1,
        pageSize:10,
        tab:0,
        catalogId:null,
        checkIdList:[],
        auditOpinion:'',
        optionList:[],
        resourceMeteData:{},
        comList:[],
        look:false,
        yuanshujuList:[],
        ysjjList:[],
        fenlSelect1:[],
        selectMeteSetRow:null,
        loading:true
    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        },
        filterText1:function(val) {
            this.$refs.tree1.filter(val);
        }
    },
    methods: {
        query1: function () {
            vm.getTableList1();
        },
        clean1:function () {
            vm.q1 = {
                cnName:'',
                meteSetNumber:'',
                meteCategorySetId:'',

            };
            vm.getTableList1();
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
            vm.getMenuList();
            vm.look = false;
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "/xj/xjcatalog/list", function(r){
                // r.push({
                //     parentId:-1,
                //     catalogId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("catalogId", vm.resourceMeteData.catalogId);
                ztree.selectNode(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.resourceMeteData.parentId = node[0].catalogId;
                    vm.resourceMeteData.parentName = node[0].catalogName;
                    // vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        getMenu1: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                // r.push({
                //     parentId:-1,
                //     meteCategoryId:0,
                //     name:'一级目录'
                // })
                ztree1 = $.fn.zTree.init($("#menuTree1"), setting1, r);
                var node = ztree1.getNodeByParam("meteCategoryId", vm.resourceMeteData.categoryId);
                ztree1.selectNode(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree1: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer1"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree1.getSelectedNodes();
                    //选择上级菜单
                    vm.resourceMeteData.categoryId = node[0].meteCategoryId;
                    vm.resourceMeteData.categoryName = node[0].name;
                    vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        // 获取资源提供方单位
        getComList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/organisationinfo/select',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:1,
                },
                success: function(r){
                    if(r.code === 0){
                        vm.comList = r.list;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 设置资源提供方信息
        setCom:function (obj) {
            vm.resourceMeteData.organisationName = obj.organisationName;
            vm.resourceMeteData.organisationId = obj.organisationId;
            vm.resourceMeteData.organisationAddress = obj.organisationAddr;

        },
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
        openSwitch1:function () {
            if(vm.open1){
                vm.open1 = false;
                vm.openText1 = '收起筛选'

            }else {
                vm.open1 = true;
                vm.openText1 = '展开筛选'
            }
        },
        getInfo: function(catalogId){
            $.get(baseURL + "xj/xjcatalog/info/"+catalogId, function(r){
                vm.resourceMeteData = r.xjCatalog;
                // vm.resourceMeteData.parentId = 0;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        update: function (id) {
            var catalogId = id;
            if(catalogId == null){
                return ;
            }
            //
            vm.showList = false;
            vm.title = "修改目录";

            vm.getInfo(catalogId);
            vm.getMenu();
            vm.getMenu1();
            vm.getComList();
        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.catalogId == ''  ? "xj/xjcatalog/save" : "xj/xjcatalog/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.resourceMeteData),
                success: function(r){
                    if(r.code === 0){
                        vm.page = 1;
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        filterNode:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取
        getMenuList: function (event) {
            $.getJSON(baseURL + "xj/xjcatalog/list", function(r){
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
            var obj = {
                page:this.page,
                reviewState:null,
                pushState:null,
                name:this.nameS,
                catalogId:this.catalogId
            }
            if(this.tab == 4){
                obj = {
                    page:this.page,
                    name:this.nameS,
                    reviewState:null,
                    pushState:1,
                    catalogId:this.catalogId
                }
            }else {
                obj = {
                    page:this.page,
                    name:this.nameS,
                    reviewState:this.tab,
                    pushState:null,
                    catalogId:this.catalogId
                }
            }
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjcatalog/page',
                // contentType: "application/json",
                dataType: 'json',
                data: obj,
                success: function(r){
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                        vm.loading = false;
                    }else{
                        alert(r.msg);
                        vm.loading = false;
                    }
                }
            });
        },

        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getTableList();
        },
        // 编辑分页
        layerPage1:function (currentPage) {
            vm.page1 = currentPage;
            vm.getTableList1();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            vm.catalogId = data.id;
            vm.getTableList();

            // if(data.list.length == 0 || data.id == null){
            //
            //     vm.catalogId = data.id;
            //     if(data.name == '资源目录'){
            //         vm.nameS = '';
            //     }else {
            //         vm.nameS = data.name;
            //     }
            //
            //     vm.getTableList();
            // }
        },
        // 选项卡
        tabClick:function (num) {
            vm.tab = num;
            vm.page = 1;
            vm.getTableList();
        },
        // 表格选中方法
        toggleSelection:function(selection) {
            vm.checkIdList = selection;
        },
        // 查看
        lookC:function (id) {
            vm.look = true;
            vm.showList = false;
            vm.title = "目录详情";

            vm.getMenu();
            vm.getMenu1();
            vm.getComList();
            vm.getInfo(id);
        },
        // 提交
        subMit:function () {
            var that = this;
            var list = [];
            vm.checkIdList.forEach(function (item) {
                list.push(item.catalogId)
            })
            if(list.length == 0){
                this.$message({
                    message: '请选择一条记录',
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
                    type: "post",
                    url: baseURL + 'xj/xjcatalog/submit',
                    contentType: "application/json",
                    // dataType: 'json',
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 1;
                            vm.page = 1;
                            vm.getTableList();
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            }

        },
        // 撤回
        revoke:function (id) {
            var that = this;
            layer.confirm('确定要撤回该条记录？', function(index){
                const loading = that.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                $.ajax({
                    type: "get",
                    url: baseURL + 'xj/xjcatalog/revoke',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        catalogId:id
                    },
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 0;
                            vm.page = 1;
                            vm.getTableList();
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });


        },
        // 通过方法
        agree:function (id) {
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjcatalog/agree',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    catalogId:id
                },
                // data:JSON.stringify([id]),
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 2;
                        vm.page = 1;
                        vm.getTableList();
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 拒绝
        refuse:function (id) {
            var that = this;
            layer.open({
                type: 1,
                title: '审核意见',
                content: $('#refuseDiv'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '260px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    $.ajax({
                        type: "post",
                        url: baseURL + 'xj/xjcatalog/refuse',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify({
                            catalogId:id,
                            auditOpinion:vm.auditOpinion
                        }),
                        success: function(r){
                            if(r.code === 0){
                                vm.tab = 3;
                                vm.page = 1;
                                vm.getTableList();
                                layer.close(index);
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }else{
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });

                },
                btn2:function () {
                    // layer.close(index);
                    vm.getTableList();
                }

            })

        },
        // 发布
        push:function (id) {
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjcatalog/push',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    catalogId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 4;
                        vm.page = 1;
                        vm.getTableList();
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 停止发布
        stopPush:function (id) {
            var that = this;
            layer.confirm('确定要停止发布该条记录？', function(index){
                const loading = that.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                $.ajax({
                    type: "get",
                    url: baseURL + 'xj/xjcatalog/stopPush',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        catalogId:id
                    },
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 2;
                            vm.page = 1;
                            vm.getTableList();
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            })

        },
        // 获取审核记录
        getoptionList:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjcatalogaudit/getList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    catalogId:id
                },
                success: function(r){
                    vm.optionList = r;
                    layer.open({
                        type: 1,
                        title: '审核记录',
                        content: $('#shenheList'), //这里content是一个普通的String
                        skin: 'openClass',
                        area: ['562px', '460px'],
                        shadeClose: true,
                        closeBtn:0,
                        btn: ['关闭'],
                        btn1:function (index) {

                            layer.close(index);
                        }

                    })
                }
            });
        },

        // 获取元数据列表
        getyuanshujuList:function(id){
            $.get(baseURL + "xj/xjmetadataset/info/"+id, function(r){
                vm.resourceMeteData.meteDataList = [];
                r.xjMetaDataSet.meteDataList.forEach(function (t) {
                    vm.resourceMeteData.meteDataList.push({
                        meteNumber:t.meteNumber,
                        meteCname:t.cnName,
                        meteEname:t.euName,
                        meteEuShortName:t.euShortName,
                        updateTime:t.updateTime
                    })
                })
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        // 打开选择元数据集
        getYsj:function () {
            vm.getMenuList1();
            vm.getTableList1();

            layer.open({
                type: 1,
                title: '选择元数据集',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['1000px', '700px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    vm.resourceMeteData.meteSetId = vm.selectMeteSetRow.meteSetId;
                    vm.resourceMeteData.meteSetName = vm.selectMeteSetRow.cnName;
                    layer.close(index);
                    vm.page1 = 1;
                    vm.getyuanshujuList(vm.selectMeteSetRow.meteSetId);
                },
                btn2:function () {
                    vm.page1 = 1;
                }

            })
            var _height = $('.switchIn.up').height();
            var height = _height + 45 + 70;
            vm.h1 = height;
        },
        // 表格单选
        handleCurrentChange:function (row) {
            vm.selectMeteSetRow = row;
        },
        // 元数据集分类数目录
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjmetesetcategory/list", function(r){
                r.forEach(function(item,i){
                    vm.fenlSelect1.push({
                        name:item.name,
                        id:item.meteCategorySetId,
                        list:[]
                    })
                })

                var _list = [{
                    name:'元数据分类',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.fenlSelect1;
                vm.menuList1 = _list;
            });
        },
        // 获取元数据表格列表
        getTableList1:function () {
            var _this = this;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadataset/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page1,
                    meteCategorySetId:this.q1.meteCategorySetId,
                    meteSetNumber:this.q1.meteSetNumber,
                    cnName:this.q1.cnName,
                    reviewState:2
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList1 = r.page.list;
                        vm.totalPage1 = r.page.totalCount;
                        vm.tableList1.forEach(function (t,i) {
                            if(t.meteSetId == vm.resourceMeteData.meteSetId){
                                _this.$refs.multipleTable.setCurrentRow(vm.tableList1[i]);
                            }
                        })
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 元数据集分类树目录点击事件
        handleNodeClick1:function(data) {
            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                // vm.meteCategorySetId = data.id;
                vm.q1.meteCategorySetId = data.id;
                vm.getTableList1();
            }

        },
        filterNode1:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();

        // this.h = height
    }
});


