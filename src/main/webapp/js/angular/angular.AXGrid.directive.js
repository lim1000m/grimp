/**
 * Angularjs - AXGrid Directive
 * 
 * Copyright (c) 2016 Jinhyoung Lee(polyam@eseict.com)

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

 * Directive version : 0.5
 * 
 */
AXConfig.AXGrid.fitToWidthRightMargin = -1;
angular.module("ax-grid-module", [])
	.directive("axGrid", ['$log', '$q', '$compile', '$parse', 'axGridHelper', function($log, $q, $compile, $parse, axGridHelper){
		var preLink = function (scope, element, attrs, ngModel){
			console.log(arguments);
		}
		
		var postLink = function (scope, element, attrs, ngModel){
			console.log(arguments);
			scope.getAxGrid = axGridHelper.getAxGrid;
			getOptions = axGridHelper.getOptions;
			
			var userGridOptions = null;
			var targetAxGrid = null;
			if(angular.isArray(scope.gridOptions)){
				var optionIndex = angular.isDefined(attrs.axGrid)? attrs.axGrid : 0;
				userGridOptions = angular.extend(getOptions(scope.gridOptions[optionIndex]), {targetID : attrs.id});
				targetAxGrid = axGridHelper.getAxGrid(optionIndex);
			}else{
				userGridOptions = angular.extend(getOptions(scope.gridOptions), {targetID : attrs.id});
				targetAxGrid = axGridHelper.getAxGrid();
			}
			
			targetAxGrid.setConfig(userGridOptions);
			
		}
		
		return {
			restrict : 'A',
			link : postLink
		};
	}]);
	
angular.module("ax-grid-module")
	.factory('axGridHelper', ['$q', function($q){
		var axGridAr = [];
		
		var getAxGrid = function(index){
			
			if(index == null || index === 'undefined'){
				index = 0;
			}
			if(axGridAr.length < index){
				for(i=axGridAr.length; i<index; i++){
					acGridAr.push(null);
				}
			}else{
				if(axGridAr[index] == null){
					axGridAr[index] = new AXGrid();
				}
			}
			return axGridAr[index];
		}
		
		var defaultOptions = {
				colGroup : [
							{key:"col1",    label:"Colume1",   width:"100", align:"right"},
							{key:"col2",    label:"Colume2",   width:"100",},
							{key:"col3",    label:"Colume3",   width:"100"},
							{key:"col4",    label:"Colume4",   width:"100"},
							{key:"col5",    label:"Colume4",   width:"100"},
							{key:"col6",    label:"Colume5",   width:"100", align:"right", formatter:"money"},
							{key:"col7",    label:"Colume6",   width:"100",  align:"right", formatter:"money"}
					],
				fitToWidth : true,
				colHeadAlign : "center",
				//height : "auto",
				page:{
					paging  : true, // {Boolean} -- 페이징 사용여부를 설정합니다.
					pageNo  : 1,    // {Number} -- 현재 페이지 번호를 설정합니다.
					pageSize: 10,  // {Number} -- 한 페이지장 표시할 데이터 수를 설정합니다.
					onchange: function(pageNo){} // {Funtion} -- 페이지 변경 이벤트입니다.
				}
			} 
		var getOptions = function(userOptions){
			
			return angular.extend({}, defaultOptions, userOptions)
		}
		
		return {
			getAxGrid : getAxGrid,
			getOptions : getOptions
		};
	}]);