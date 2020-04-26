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
            rootPId: -1
        },
        key: {
            url:"nourl"
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
            meteNumber:'',
            meteCategoryId:'',
            cnName:''
        },
        showList: true,
        title: null,
        resourceMeteData: {},
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
        tableListUp:[],
        totalPage:0,
        totalPage1:0,
        page:1,
        page1:1,
        pageSize:10,
        tab:0,
        checkIdList:[],
        checkIdList1:[],
        catalogId:null,
        fileData:{},
        comList:[],
        fenlSelect:[],
        controlTypeList:[],
        dataTypeList:[],
        id:1,
        hisList:[]
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
                meteNumber:'',
                meteCategoryId:'',
                cnName:''
            };
            vm.getTableList();

        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/resourcecatalog/list", function(r){
                console.log(r);
                // r.push({
                //     parentId:-1,
                //     catalogId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("catalogId", vm.resourceMeteData.catalogId);
                ztree.selectNode(node);
                console.log(node);
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
                    console.log(node);
                    //选择上级菜单
                    vm.resourceMeteData.catalogId = node[0].catalogId;
                    vm.resourceMeteData.catalogName = node[0].name;
                    // vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        getMenu1: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                console.log(r);
                // r.push({
                //     parentId:-1,
                //     meteCategoryId:0,
                //     name:'一级目录'
                // })
                ztree1 = $.fn.zTree.init($("#menuTree1"), setting1, r);
                var node = ztree1.getNodeByParam("meteCategoryId", vm.resourceMeteData.categoryId);
                ztree1.selectNode(node);
                console.log(node);
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
                    console.log(node);
                    //选择上级菜单
                    vm.resourceMeteData.categoryId = node[0].meteCategoryId;
                    vm.resourceMeteData.categoryName = node[0].name;
                    vm.resourceMeteData.catagoryCode = node[0].code;
                    console.log(vm.resourceMeteData.catagoryCode);
                    layer.close(index);
                }
            });
        },
        add: function(){

            vm.showList = false;
            vm.title = "新增元数据";
            if(vm.catalogId){
                vm.resourceMeteData.meteCategoryId = vm.catalogId;
            }
            // vm.getMenu();
            // vm.getMenu1();
            // vm.getComList();
        },
        update: function (id) {
            var meteId = id;
            if(meteId == null){
                return ;
            }
            //
            vm.showList = false;
            vm.title = "修改元数据";

            vm.getInfo(meteId);
            // vm.getMenu();
            // vm.getMenu1();
            // vm.getComList();
        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.meteId == null ? "xj/xjmetadata/save" : "xj/xjmetadata/update";
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
        del: function (id) {
            var list = [];
            list.push(id)

            layer.confirm('确定要删除选中的记录？', function(index){
                $.ajax({
                    type: "POST",
                    url: baseURL + "xj/xjmetadata/delete",
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
        getInfo: function(meteId){
            $.get(baseURL + "xj/xjmetadata/info/"+meteId, function(r){
                console.log(r);
                vm.resourceMeteData = r.xjMetaData;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
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
                    console.log(r);
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
            console.log(obj);
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
            $.getJSON(baseURL + "xj/xjmetecategory/list", function(r){

                r.forEach(function(item,i){
                    vm.fenlSelect.push({
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
                _list[0].list = vm.fenlSelect;
                vm.menuList = _list;
                console.log(vm.menuList);
            });
        },
        // 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadata/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    meteNumber:this.q.meteNumber,
                    meteCategoryId:this.q.meteCategoryId,
                    cnName:this.q.cnName
                },
                success: function(r){
                    console.log(r);
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
            console.log(currentPage);
            vm.page = currentPage;
            vm.getTableList();
        },
        // 编辑分页
        layerPage1:function (currentPage) {
            console.log(currentPage);
            vm.page1 = currentPage;
            vm.getFileTableList();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            console.log(data);
            console.log(JSON.stringify(data.id) == 'null');

            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                console.log('进来了')
                vm.catalogId = data.id;
                vm.q.meteCategoryId = data.id;
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
        // 提交
        subMit:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteId)
            })
            console.log(list);
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
        // 新增
        addUp:function () {
            vm.fileData = {};
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['600px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate1();
                    layer.close(index);
                },
                btn2:function () {
                    vm.getFileTableList();
                }

            })
        },
        // 删除
        delUp:function () {
            var list = []
            vm.checkIdList1.forEach(function (item) {
                list.push(item.meteId)
            })
            console.log(list);
            if(list.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                layer.confirm('确定要删除选中的记录？', function(index){
                    $.ajax({
                        type: "POST",
                        url: baseURL + "resource/resourcefield/delete",
                        contentType: "application/json",
                        data: JSON.stringify(list),
                        success: function(r){
                            if(r.code == 0){
                                layer.close(index);
                                vm.getFileTableList();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操111作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:100});
                            }else{
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                });
            }


        },
        // 保存方法
        saveOrUpdate1: function (event) {
            var url = vm.fileData.fieldId == null ? "resource/resourcefield/save" : "resource/resourcefield/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.fileData),
                success: function(r){
                    if(r.code === 0){
                        vm.page1 = 1;
                        vm.getFileTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作11成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:1000});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 获取信息方法
        getFileInfo:function (fileId) {
            $.get(baseURL + "resource/resourcefield/info/"+fileId, function(r){
                console.log(r);
                vm.fileData = r.resourceField;
            });
        },
        // 批量设置
        setUp:function () {
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['600px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate1();
                    layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
        },
        // 修改
        editUp:function (id) {
            vm.getFileInfo(id);
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['600px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate1();
                    layer.close(index);
                },
                btn2:function () {
                    vm.getFileTableList();
                }

            })

        },
        // 获取文件列表
        getFileTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcefield/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:vm.page1,
                    id:vm.resourceMeteData.meteId
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.resourceMeteData.fieldList = r.page.list;
                        vm.totalPage1 = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 导入完成
        handleAvatarSuccess:function(res, file) {
            // vm.imageUrl = URL.createObjectURL(file.raw);
            // vm.file = file;
            console.log(res);
            if(res.code == 0){
                this.$message({
                    type: 'success',
                    message: '导入成功！'
                });
                vm.getFileTableList();
            }
        },
        // 导入前
        beforeAvatarUpload:function(file) {
            var FileExt = file.name.replace(/.+\./, "");
            if (['xlsx','xls'].indexOf(FileExt.toLowerCase()) === -1){
                this.$message({
                    type: 'warning',
                    message: '上传文件只能是excel！'
                });
                return false;
            }else {
                file.type = 'xls';
                vm.fileData = file;
                console.log(vm.fileData);
            }
        },
        handlePreview:function(file) {
            var FileExt = file.name.replace(/.+\./, "");
            if (['xlsx','xls'].indexOf(FileExt.toLowerCase()) === -1){
                this.$message({
                    type: 'warning',
                    message: '上传文件只能是excel！'
                });
                return false;
            }else {
                file.type = 'xls';
                vm.fileData = file;
                console.log(vm.fileData);
                vm.inUp();
            }
        },
        inUp:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcefield/importField',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    file:vm.fileData,
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        // vm.resourceMeteData.fieldList = r.page.list;
                        // vm.totalPage1 = r.page.totalPage;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 导出
        outUp:function () {
            window.location.href = baseURL + "xj/xjmetadata/downField/"+vm.id
        },
        // 下载模版
        downUp:function () {
            window.location.href = baseURL + "resource/resourcefield/downTemplate"
        },
        // 表格选中方法
        toggleSelection1:function(selection) {
            console.log(selection);
            vm.checkIdList1 = selection;
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
                    console.log(r);
                    if(type == 'control_type'){
                        vm.controlTypeList = r;
                    }else if(type == 'data_type'){
                        vm.dataTypeList =r;
                    }
                }
            });
        },
        // 禁用
        closeC:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteId)
            })
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                layer.confirm('确定要禁用选中的元数据？', function(index32){
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'xj/xjmetadata/updateDisabledState',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify(list),
                        success: function(r){
                            console.log(r);
                            if(r.code == 0){
                                layer.close(index32);
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                                vm.getTableList();
                            }else {
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                })
            }

        },
        // 启用
        openC:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteId)
            })
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                layer.confirm('确定要启动选中的元数据？', function(index31){
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'xj/xjmetadata/updateEnabledState',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify(list),
                        success: function(r){
                            console.log(r);
                            if(r.code == 0){
                                layer.close(index31);
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                                vm.getTableList();
                            }else {
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }

                        }
                    });
                })
            }

        },
        // 获取历史版本
        getHist:function (id) {
            var _this = this;
            $.get(baseURL + "xj/xjmetadata/historyInfo/"+id, function(r){
                console.log(r);
                if(r.hList.length == 0){
                    _this.$message({
                        message: '暂无历史版本',
                        type: 'warning'
                    });
                }else {
                    vm.hisList = r.hList;
                    layer.open({
                        type: 1,
                        title: '历史版本',
                        content: $('#hisList'), //这里content是一个普通的String
                        skin: 'openClass',
                        area: ['1000px', '580px'],
                        shadeClose: true,
                        closeBtn:0,
                        btn: ['关闭'],
                        btn1:function (index) {
                            layer.close(index);
                        },
                        btn2:function () {
                        }

                    })
                }

                // vm.tableListUp = r.resourceMeteData.list;
            });

        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();
        this.dictClick('control_type');
        this.dictClick('data_type');

        // this.h = height
    }
});


