$(document).ready(function(){
	initAjax(1); //1=1페이지를 default로

	function initAjax(page) {
		$.ajax({
			url : "board_list_json_data.do?page="+page, //board_list_json_data에서 넘어오는 데이터가 result
			success : function(result){
				//alert(result); 					//문자인 상태;사용하려면 json형태여야함
				let jdata = JSON.parse(result); //result로 넘어온 데이터 형태가 string이면 JSON으로 변환?-> jdata는 JSON 객체로 변환
				//alert(jdata.jlist[0].rno); //JSON 객체
				
				//dhtml을 활용하여 테이블로 출력
				let output = "<table class='board_list'>";
				output += "<tr><td colspan='5'>";
				output += "<a href='board_write.do'>";
				output += "<button type='button' class='btn_style2'>글쓰기</button>";
				output += "</a></td></tr>";
				output += "<tr><th>번호</th><th>제목</th><th>조회수</th><th>작성자</th><th>작성일자</th></tr>";
				for(obj of jdata.jlist) {
					output += "<tr>";
					output += "<td>"+obj.rno+"</td>";
					output += "<td><a href='board_content.do?bid="+obj.bid+"'>"+obj.btitle+"</a></td>";
					output += "<td>"+obj.bhits+"</td>";
					output += "<td>"+obj.id+"</td>";
					output += "<td>"+obj.bdate+"</td>";
					output += "</tr>";
				}
				output += "<tr>";
				output += "<td colspan='5'><div id='ampaginationsm'></div></td>";
				output += "</tr>";
				output += "</table>";
				
				//output body안에 출력
				$("table.board_list").remove();
				$("h1").after(output);
				
				//페이징 처리 함수 호출
				pager(jdata.totals, jdata.maxSize, jdata.pageSize, jdata.page);
				
				//페이지 번호 클릭 이벤트 처리
				jQuery('#ampaginationsm').on('am.pagination.change',function(e){
			 		  jQuery('.showlabelsm').text('The selected page no: '+e.page);
	          		 //$(location).attr('href', "http://localhost:9000/mycgv_jsp/board_list_json.do?page="+e.page);
	          		 
	          		 initAjax(e.page);
	 			   });
			}//success
			
		}); //ajax
	} //initAjax
	
	/* 페이징 처리 함수 */
	function pager(totals, maxSize, pageSize, page) {
		//alert(totals+","+maxSize+","+pageSize+","+page);
		var pager = jQuery('#ampaginationsm').pagination({
		
		    maxSize: maxSize,		// max page size(pageCount)
		    totals: totals,			// total pages(total rows;dbCount)	
		    page: page,				// initial page(reqPage)	
		    pageSize: pageSize,		// max number items per page
		
		    // custom labels		
		    lastText: '&raquo;&raquo;', 		
		    firstText: '&laquo;&laquo;',		
		    prevText: '&laquo;',		
		    nextText: '&raquo;',
				     
		    btnSize:'sm'			// 'sm'  or 'lg'		
		});
	} //function pager


}); //ready