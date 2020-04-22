$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xj/xjmetadata/list',
        datatype: "json",
        colModel: [			
			{ label: 'fieldId', name: 'fieldId', index: 'field_id', width: 50, key: true },
			{ label: '编号', name: 'meteId', index: 'mete_id', width: 80 }, 			
			{ label: '中文名称', name: 'cnName', index: 'cn_name', width: 80 }, 			
			{ label: '英文名称', name: 'euName', index: 'eu_name', width: 80 }, 			
			{ label: '英文短名', name: 'euShortName', index: 'eu_short_name', width: 80 }, 			
			{ label: '数据类型', name: 'dataType', index: 'data_type', width: 80 }, 			
			{ label: '控件类型', name: 'controlType', index: 'control_type', width: 80 }, 			
			{ label: '校验类型（0-不验证，1-验证）', name: 'checkType', index: 'check_type', width: 80 }, 			
			{ label: '数据长度', name: 'dataLength', index: 'data_length', width: 80 }, 			
			{ label: '值域', name: 'range', index: 'range', width: 80 }, 			
			{ label: '值域说明', name: 'rangeDescription', index: 'range_description', width: 80 }, 			
			{ label: '是否必选还是非必选', name: 'judgeMandatory', index: 'judge_mandatory', width: 80 }, 			
			{ label: '定义', name: 'definition', index: 'definition', width: 80 }, 			
			{ label: '是否禁用（0-不禁用；1-禁用）', name: 'isDisabled', index: 'is_disabled', width: 80 }, 			
			{ label: '创建用户id', name: 'createUserId', index: 'create_user_id', width: 80 }, 			
			{ label: '创建日期', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '更新日期', name: 'updateTime', index: 'update_time', width: 80 }			
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
		xjMetaData: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.xjMetaData = {};
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
			var url = vm.xjMetaData.fieldId == null ? "xj/xjmetadata/save" : "xj/xjmetadata/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.xjMetaData),
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
				    url: baseURL + "xj/xjmetadata/delete",
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
			$.get(baseURL + "xj/xjmetadata/info/"+fieldId, function(r){
                vm.xjMetaData = r.xjMetaData;
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