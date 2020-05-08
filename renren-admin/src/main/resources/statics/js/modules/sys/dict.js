$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dict/list',
        datatype: "json",
        colModel: [			
			{ label: '字典名称', name: 'name', index: 'name', width: 80 },
			{ label: '字典类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '字典码', name: 'code', index: 'code', width: 80 }, 			
			{ label: '字典值', name: 'value', index: 'value', width: 80 }, 			
			{ label: '排序', name: 'orderNum', index: 'order_num', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }
		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: false,
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            name: null
        },
		showList: true,
		title: null,
		dict: {},
        open:true,
        openText:'展开筛选',
        h:0,
        tableList:[],
        totalPage:0,
		page:1,
        checkIdList:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
        clean:function () {
            vm.q = {
                name: null
            };
            vm.getList();
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
		add: function(){
			// vm.showList = false;
			var that = this;
			vm.dict = {
			    name:'',
                type:'',
                code:'',
                value:''
            };
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '460px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    if(JSON.stringify(vm.dict) == '{}' || vm.dict.name == '' || vm.dict.type == '' || vm.dict.code == '' || vm.dict.value == '' ){
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
                            url: baseURL + 'sys/dict/save',
                            contentType: "application/json",
                            data: JSON.stringify(vm.dict),
                            success: function(r){
                                if(r.code === 0){
                                    layer.close(index);
                                    loading.close();
                                    vm.getList();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
                                }else{
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                                }
                            }
                        });
                    }


                    // layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
		},
		update: function (id) {
			var id = id;
            var that = this;
            layer.open({
                type: 1,
                title: '修改',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '460px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['修改','取消'],
                btn1:function (index) {
                    if(JSON.stringify(vm.dict) == '{}' || vm.dict.name == '' || vm.dict.type == '' || vm.dict.code == '' || vm.dict.value == '' ){
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
                            url: baseURL + 'sys/dict/update',
                            contentType: "application/json",
                            data: JSON.stringify(vm.dict),
                            success: function(r){
                                if(r.code === 0){
                                    vm.getList();
                                    layer.close(index);
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
                                }else{
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                                }
                            }
                        });
                    }
                },
                btn2:function () {
                    vm.reload();
                }

            })
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dict),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = vm.checkIdList;
			if(ids == null){
				return ;
			}
            var that = this;
			confirm('确定要删除选中的记录？', function(){
                const loading = that.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/dict/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
						}else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/dict/info/"+id, function(r){
                vm.dict = r.dict;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
		getList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'sys/dict/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                	page:this.page,
                    name:this.q.name,
                },
                success: function(r){
                	console.log(r);
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
        toggleSelection:function (selection) {
			console.log(selection);
            vm.checkIdList = [];
            selection.forEach(function (item) {
                vm.checkIdList.push(item.id)
            })
        },
        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getList();
        },
	},
    created:function () {
        this.getList();

        // this.h = height
    }
});