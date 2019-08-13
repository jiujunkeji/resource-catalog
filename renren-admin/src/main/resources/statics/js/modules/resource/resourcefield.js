$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'resource/resourcefield/list',
        datatype: "json",
        colModel: [			
			{ label: 'fieldId', name: 'fieldId', index: 'field_id', width: 50, key: true },
			{ label: '', name: 'meteId', index: 'mete_id', width: 80 }, 			
			{ label: '中文名称', name: 'cnName', index: 'CN_name', width: 80 }, 			
			{ label: '英文名称', name: 'euName', index: 'EU_name', width: 80 }, 			
			{ label: '数据类型', name: 'datType', index: 'dat_type', width: 80 }, 			
			{ label: '数据长度', name: 'dataLength', index: 'data_length', width: 80 }, 			
			{ label: '判断必选', name: 'judgeMandatory', index: 'judge_mandatory', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }			
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
		resourceField: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.resourceField = {};
		},
		update: function (event) {
			var fieldId = getSelectedRow();
			if(fieldId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(fieldId)
		},
		saveOrUpdate: function (event) {
			var url = vm.resourceField.fieldId == null ? "resource/resourcefield/save" : "resource/resourcefield/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.resourceField),
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
			var fieldIds = getSelectedRows();
			if(fieldIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/resourcefield/delete",
                    contentType: "application/json",
				    data: JSON.stringify(fieldIds),
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
		getInfo: function(fieldId){
			$.get(baseURL + "resource/resourcefield/info/"+fieldId, function(r){
                vm.resourceField = r.resourceField;
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