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
        q: {
            name:'',
            safeTypeCode:'',
            safe:'',
            encryptCode:''
        },
        q1: {
            name:'',
            meteNumber:'',
            meteCategoryId:'',
            cnName:''
        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteType:null,
            categoryId:null,
            categoryName:'',
            catagoryCode:'',
            catalogId:'',
            catalogName:'',
            fieldList:[],
            parentName:'',
            parentId:0,
            isUsed:1
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        open1:true,
        openText1:'展开筛选',
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
        tableList1:[],
        totalPage:0,
        totalPage1:0,
        page:1,
        page1:1,
        pageSize:10,
        tab:0,
        checkIdList:[],
        checkIdList1:[],
        checkIdList2:[],
        fenlSelect:[],
        fenlSelect1:[],
        catalogId:null,
        fileData:{
            cnName:'',
            safe:{}
        },
        comList:[],
        safeLevelList:[],
        encryptMethodList:[],
        safeTypeList:[]

    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        clean:function () {
            vm.q = {
                name:'',
                safeTypeCode:'',
                safe:'',
                encryptCode:''
            };
            vm.getTableList();
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
                    vm.resourceMeteData.catalogId = node[0].catalogId;
                    vm.resourceMeteData.catalogName = node[0].catalogName;
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
        add: function(){
            vm.showList = false;
            vm.title = "新增目录安全设置";
            vm.resourceMeteData = {
                meteType:null,
                categoryId:null,
                categoryName:'',
                catagoryCode:'',
                catalogId:'',
                catalogName:'',
                fieldList:[],
                parentId:0,
                parentName:''
            };
            vm.getMenu();
            // vm.getMenu1();
            vm.getComList();

        },
        update: function (id) {
            var catalogId = id;
            if(catalogId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改目录安全设置";
            vm.getInfo(catalogId);
            vm.getMenu();
            // vm.getMenu1();
            // vm.getComList();
        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.safeId == null  ? "xj/xjsafe/save" : "xj/xjsafe/update";
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
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败<br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        del: function (id) {
            var list = [];
            list.push(id)

            layer.confirm('确定要删除选中的记录？', function(index){
                $.ajax({
                    type: "POST",
                    url: baseURL + "xj/xjsafe/delete",
                    contentType: "application/json",
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:1000});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });
        },
        getInfo: function(catalogId){
            $.get(baseURL + "xj/xjsafe/info/"+catalogId, function(r){
                vm.resourceMeteData = r.xjSafe;
                // vm.resourceMeteData.parentId = 0;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
            vm.getMenuList();
        },
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
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
                        console.log(1111);
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
                        console.log(222);
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
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjsafe/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    name:this.q.name,
                    safeTypeCode:this.q.safeTypeCode,
                    safeCode:this.q.safeCode,
                    encryptCode:this.q.encryptCode,
                    catalogId:this.catalogId
                },
                success: function(r){
                    // vm.tableList = r
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                    }else{
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
        // 编辑分页
        layerPage1:function (currentPage) {
            vm.page1 = currentPage;
            vm.getFileTableList();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            vm.catalogId = data.id;
            vm.getTableList();

            // if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
            //     vm.catalogId = data.id;
            //     // vm.q.name = data.name;
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
        // 提交
        subMit:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteId)
            })
            if(list.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                $.ajax({
                    type: "post",
                    url: baseURL + 'resource/resourcemetedata/submit',
                    contentType: "application/json",
                    // dataType: 'json',
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 1;
                            vm.page = 1;
                            vm.getTableList();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            }

        },
        // 撤回
        revoke:function (id) {
            var list = []
            vm.menuList.forEach(function (item) {
                list.push(item.meteId)
            })
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/revoke',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 0;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },

        // 编辑方法
        query1: function () {
            vm.getTableList1();
        },
        clean1:function () {
            vm.q1 = {
                name:'',
                meteNumber:'',
                meteCategoryId:'',
                cnName:''
            }
        },
        // 收缩展开搜索
        openSwitch1:function () {
            if(vm.open1){
                vm.open1 = false;
                vm.openText1 = '收起筛选'

            }else {
                vm.open1 = true;
                vm.openText1 = '展开筛选'
            }
        },
        filterNode1:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取元数据分类列表
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjmetecategory/list", function(r){

                r.forEach(function(item,i){
                    vm.fenlSelect1.push({
                        name:item.name,
                        id:item.meteCategoryId,
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
        // 树目录点击事件
        handleNodeClick1:function(data) {
            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                vm.q.meteCategoryId = data.id;
                vm.getTableList1();
            }

        },
        // 获取元数据列表
        getTableList1:function () {
            var _this = this;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadata/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page1,
                    meteNumber:this.q1.meteNumber,
                    meteCategoryId:this.q1.meteCategoryId,
                    cnName:this.q1.cnName
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList1 = [];
                        vm.totalPage1 = r.page.totalCount;
                        r.page.list.forEach(function (item) {
                            if(item.isDisabled == 0){
                                vm.tableList1.push(item)
                            }
                        })

                        if (vm.resourceMeteData.meteDataList.length != 0) {
                            vm.resourceMeteData.meteDataList.forEach(function (item) {
                                vm.tableList1.forEach(function (m,n) {
                                    if(m.meteId == item.meteId){
                                        _this.$nextTick(function () {
                                            this.$refs.multipleTable.toggleRowSelection(this.$refs.multipleTable.data[n],true);
                                        })
                                    }
                                })
                                // _this.$refs.multipleTable.toggleRowSelection(t,true);
                            });
                        } else {
                            _this.$refs.multipleTable.clearSelection();
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        addUp:function () {
            vm.getMenuList1();
            vm.getTableList1();

            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['1000px', '660px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    // vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    layer.close(index);
                },
                btn2:function () {

                }

            })
        },
        delUp:function () {
            var arr =[];
            vm.checkIdList1.forEach(function (item,i) {
                vm.resourceMeteData.meteDataList.forEach(function (m,n) {
                    if(m.meteId == item.meteId){
                        vm.resourceMeteData.meteDataList.splice(n,1);
                        return
                    }
                })
            })
            // arr.forEach(function (item) {
            //     vm.resourceMeteData.meteDataList.splice(item,1)
            // })

        },
        // 导出
        outUp:function () {
            window.location.href = baseURL + "xj/xjmetadataset/downField/"+vm.id
        },
        // 表格选中方法
        toggleSelection1:function(selection) {
            vm.checkIdList1 = selection;
        },
        // 表格选中方法
        toggleSelection2:function(selection) {
            vm.checkIdList2 = selection;
        },
        // 数据字典
        dictClick:function (type) {
            $.ajax({
                type: "get",
                url: baseURL + "sys/dict/selectDict",
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    type:type
                },
                success: function(r){
                    if(type == 'safe_level'){
                        vm.safeLevelList = r;
                    }else if(type == 'encrypt_method'){
                        vm.encryptMethodList =r;
                    }else if(type == 'safe_type'){
                        vm.safeTypeList =r;
                    }
                }
            });
        },
        // 加密修改
        encryptChan:function (opt) {
            vm.encryptMethodList.forEach(function (item) {
                if(item.code == opt){
                    vm.resourceMeteData.encrypt = item.value;
                    return
                }
            })
        },
        // 安全等级修改
        safeLevelChan:function (opt) {
            vm.safeLevelList.forEach(function (item) {
                if(item.code == opt){
                    vm.resourceMeteData.safe = item.value;
                    return
                }
            })
        },
        // 安全类型修改
        safeTypeChan:function (opt) {
            vm.safeTypeList.forEach(function (item) {
                if(item.code == opt){
                    vm.resourceMeteData.safeType = item.value;
                    return
                }
            })
        },
        // 设置安全级别
        setSafe:function (row) {
            vm.fileData = {
                cnName:'',
                safe:null
            }
            vm.fileData.cnName = row.meteCname;
            layer.open({
                type: 1,
                title: '设置',
                content: $('#safeDiv'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['500px', '260px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    row.safe = vm.fileData.safe.value;
                    row.safeCode = vm.fileData.safe.code;
                    layer.close(index);
                },
                btn2:function () {

                }

            })
        },
        safeChange:function () {
            
        }
    },
    created:function () {
        this.getMenuList();
        this.getTableList();
        this.dictClick('safe_level');
        this.dictClick('encrypt_method');
        this.dictClick('safe_type');
        // this.h = height
    }
});


