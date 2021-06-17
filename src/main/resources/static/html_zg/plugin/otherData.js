$(document).ready(function(){
	function formatDate(date) { 
//		var now=new Date(date)
//		var year=now.getFullYear(); 
//		var month=now.getMonth()+1; 
//		var date=now.getDate(); 
//		return year+"-"+month+"-"+date; 
//		date.slice(0,11)
	}
//	人口态势
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/peopleTheme/getPeopleTheme",
		success:function(rs){
			$(".popudata").empty()
//			$("#poputime").text(formatDate(rs.sysTime))
//			$("#poputime").text(rs.data[0].census_date||"")
			$("#s1 ul").empty();
			$("#s2 ul").empty();
			$.each(rs.data.peopleTheme,function(index,item){
				$(".popudata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
			$.each(rs.data.birthAndDieByMonth, function(index,item) {
				month=item.dateStr.slice(-3)
				$("#s1 ul").append("<li>"+month+"出生人口：<span class='Digital font20 colornum'>"+(item.birthTotal||'')+"</span> 人 <span class='text-right time font12'>更新至:"+(item.censusDate.slice(0,11)||'')+"</span></li>")
				$("#s2 ul").append("<li>"+month+"死亡人口：<span class='Digital font20 colornum'>"+(item.dieTotal||'')+"</span> 人<span class='text-right time font12'>更新至:"+(item.censusDate.slice(0,11)||'')+"</span></li>")
			});
		}
	});
//	法人态势
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/legalTheme/getLegalTheme",
		success:function(rs){
//			$("#lawtime").text(rs.data[0].census_date||"")
			$(".lawdata").empty()
			$.each(rs.data,function(index,item){
				$(".lawdata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
//	党建动态
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/partyTheme/getPartyTheme",
		success:function(rs){
			if(rs.data.buildMap){
//				$("#partytime").text(rs.data.buildMap.deadline||"")
				$("#investment").text(rs.data.buildMap.investment||"")
				$("#investment_time").text(rs.data.buildMap.deadline.slice(0,11)||"")
			}
			if(rs.data.middleMap){
				$("#member").text(rs.data.middleMap.member||"")
				$("#org").text(rs.data.middleMap.org||"")
				$("#masses").text(rs.data.middleMap.masses||"")
				$(".time .txt").text(rs.data.middleMap.deadline.slice(0,11)||"")
			}
			if(rs.data.situationMap){
				$("#team").text(rs.data.situationMap.team||"")
				$("#organization").text(rs.data.situationMap.organization||"")
				$(".time .txt2").text(rs.data.situationMap.deadline.slice(0,11)||"")
			}
		}
	});
//	旅游动态
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/travelTheme/getTravelTheme",
		success:function(rs){
//			$("#traveltime").text(rs.data[0].census_date||"")
			$(".traveldata").empty()
			$.each(rs.data,function(index,item){
				$(".traveldata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
//	政务分析
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/governTheme/getGovernTheme",
		success:function(rs){
//			$("#affairtime").text(rs.data[0].census_date||"")
			$(".affairdata").empty()
			$.each(rs.data,function(index,item){
				$(".affairdata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
//	消防安全
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/fireTheme/getFireTheme",
		success:function(rs){
//			$("#firetime").text(rs.data[0].census_date||"")
			$(".firedata").empty()
			$.each(rs.data,function(index,item){
				$(".firedata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
//	两违监测
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/violationsTheme/getViolationsTheme",
		success:function(rs){
//			$("#monitortime").text(rs.data[0].census_date||"")
			$(".monitordata").empty()
			$.each(rs.data,function(index,item){
				$(".monitordata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
//	特殊人群
	$.ajax({
		type:"get",
		dataType: 'json',
		url:"/api/nt/specialTheme/getSpecialTheme",
		success:function(rs){
//			$("#specialtime").text(rs.data[0].census_date||"")
			$(".specialdata").empty()
			$.each(rs.data,function(index,item){
				$(".specialdata").append("<div class='ind_num  mt6'>"+(item.name||'')+"：<span class='Digital font20 colornum mt6'>"+(item.info||'')+"</span> "+(item.unit||'')+"<span class='text-right time font12'>更新至:"+(item.census_date.slice(0,11)||'')+"</span></div>")
			})
		}
	});
})
