$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'resource/cataloggrant/list',
        datatype: "json",
        colModel: [			
			{ label: 'grantId', name: 'grantId', index: 'grant_id', width: 50, key: true },
			{ label: '', name: 'deptId', index: 'dept_id', width: 80 }, 			
			{ label: '', name: 'deptName', index: 'dept_name', width: 80 }, 			
			{ label: '', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '', name: 'roleId', index: 'role_id', width: 80 }, 			
			{ label: '', name: 'roleName', index: 'role_name', width: 80 }, 			
			{ label: '', name: 'catalogId', index: 'catalog_id', width: 80 }, 			
			{ label: '', name: 'catalogName', index: 'catalog_name', width: 80 }, 			
			{ label: '', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '', name: 'createUserId', index: 'create_user_id', width: 80 }, 			
			{ label: '是否使用（0：停用；1：使用）', name: 'isUsed', index: 'is_used', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
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
		showList: true,
		title: null,
		catalogGrant: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.catalogGrant = {};
		},
		update: function (event) {
			var grantId = getSelectedRow();
			if(grantId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(grantId)
		},
		saveOrUpdate: function (event) {
			var url = vm.catalogGrant.grantId == null ? "resource/cataloggrant/save" : "resource/cataloggrant/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.catalogGrant),
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
			var grantIds = getSelectedRows();
			if(grantIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/cataloggrant/delete",
                    contentType: "application/json",
				    data: JSON.stringify(grantIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(grantId){
			$.get(baseURL + "resource/cataloggrant/info/"+grantId, function(r){
                vm.catalogGrant = r.catalogGrant;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});