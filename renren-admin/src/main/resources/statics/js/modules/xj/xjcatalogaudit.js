$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xj/xjcatalogaudit/list',
        datatype: "json",
        colModel: [			
			{ label: 'catalogAuditId', name: 'catalogAuditId', index: 'catalog_audit_id', width: 50, key: true },
			{ label: '目录id', name: 'catalogId', index: 'catalog_id', width: 80 }, 			
			{ label: '操作人id', name: 'operatUserId', index: 'operat_user_id', width: 80 }, 			
			{ label: '操作人', name: 'operatUserName', index: 'operat_user_name', width: 80 }, 			
			{ label: '操作时间', name: 'operatTime', index: 'operat_time', width: 80 }, 			
			{ label: '操作类型', name: 'operatType', index: 'operat_type', width: 80 }, 			
			{ label: '审核意见', name: 'auditOpinion', index: 'audit_opinion', width: 80 }			
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
		xjCatalogAudit: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.xjCatalogAudit = {};
		},
		update: function (event) {
			var catalogAuditId = getSelectedRow();
			if(catalogAuditId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(catalogAuditId)
		},
		saveOrUpdate: function (event) {
			var url = vm.xjCatalogAudit.catalogAuditId == null ? "xj/xjcatalogaudit/save" : "xj/xjcatalogaudit/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.xjCatalogAudit),
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
			var catalogAuditIds = getSelectedRows();
			if(catalogAuditIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xj/xjcatalogaudit/delete",
                    contentType: "application/json",
				    data: JSON.stringify(catalogAuditIds),
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
		getInfo: function(catalogAuditId){
			$.get(baseURL + "xj/xjcatalogaudit/info/"+catalogAuditId, function(r){
                vm.xjCatalogAudit = r.xjCatalogAudit;
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