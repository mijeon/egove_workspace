<%@page import="com.mj.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Board> boardList=(List)request.getAttribute("boardList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("button").click(function(){
			$(location).attr("href", "/board/write.jsp");
		})
	});
</script>
</head>
<body>

	<div class="container">
		<h2>Hover Rows</h2>
		<p>The .table-hover class enables a hover state (grey background
			on mouse over) on table rows:</p>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>No</th>
					<th>title</th>
					<th>writer</th>
					<th>regdate</th>
					<th>hit</th>
				</tr>
			</thead>
			<tbody>
			<%for(int i=0;i<boardList.size();i++){ %>
			<%Board board=boardList.get(i); %>
				<tr>
					<td><%=board.getBoard_idx() %></td>
					<td><%=board.getTitle() %>[<%=board.getCnt() %>]</td>
					
					<td><%=board.getWriter() %></td>
					<td><%=board.getRegdate() %></td>
					<td><%=board.getHit() %></td>
				</tr>
			<%} %>
			</tbody>
				<tr>
					<td colspan="5">
					<button class="btn btn-primary">글등록</button>				
					</td>
				</tr>
				
		</table>
	</div>

</body>
</html>
