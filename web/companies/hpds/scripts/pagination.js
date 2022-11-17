//Author: Isaac Pichay
function createPagination(pageNumber, totalItems, searchMethod ) {
	var paginationSpan = document.getElementById("paginationSpan");
	paginationSpan.innerHTML = "";
	var itemPerPage = document.getElementById("itemPerPage").value;
	var lastPage = 0;
	var begin = 1;
	var end = 1;
	var addUpPage = 0;
	var totalInactivePage = 4;

	if (totalItems > itemPerPage) {
		var ul = document.createElement("ul");
		ul.setAttribute("class", "pagination");

		if (totalItems % itemPerPage != 0) {
			addUpPage = 1;
		}

		var totalPages = ((totalItems - (totalItems % itemPerPage)) / itemPerPage)
				+ addUpPage;
		var end = totalPages;

		var text;
		var href;

		text = "Page " + pageNumber + " of " + totalPages;
		href = "";
		ul.appendChild(createPaginationLi(text, href));

		if ((pageNumber - 1) > 0) {
			/*href = "javascript:"+searchMethod+"(1,true)";				
			text = "<< First";				
			ul.appendChild(createPaginationLi(text,href));*/

			href = "javascript:"+searchMethod+"(" + (pageNumber - 1) + ",true)";
			text = "< Prev";
			ul.appendChild(createPaginationLi(text, href));

		}

		if (pageNumber > totalInactivePage
				&& totalPages > totalInactivePage + 1) {
			var page = 1;
			lastPage = page;
			href = "javascript:"+searchMethod+"(" + page + ",true)";
			text = page;
			ul.appendChild(createPaginationLi(text, href));
			ul.appendChild(createPaginationLi("...", null));

			begin = pageNumber
					- (totalInactivePage % 2 == 0 ? totalInactivePage / 2
							: (totalInactivePage / 2) - .5);

			if (pageNumber + (totalInactivePage / 2) < totalPages) {
				end = pageNumber
						+ (totalInactivePage % 2 == 0 ? totalInactivePage / 2
								: (totalInactivePage / 2) + .5);
			}
			if (pageNumber + (totalInactivePage / 2) > totalPages) {
				begin = pageNumber - totalInactivePage;
			}
		}

		if (pageNumber < totalInactivePage + 1
				&& totalPages > totalInactivePage) {
			end = totalInactivePage + 1;
		}

		if (pageNumber + totalInactivePage > totalPages
				&& totalPages - totalInactivePage > 0) {
			begin = totalPages - totalInactivePage;
			end = totalPages;
		}

		for ( var page = begin; page <= end; page++) {
			lastPage = page;
			var isActive = false;
			if (pageNumber == page) {
				isActive = true;
			}
			href = "javascript:"+searchMethod+"(" + page + ",true)";
			text = page;
			ul.appendChild(createPaginationLi(text, href, isActive));
		}

		if (pageNumber + (totalInactivePage - 1) < totalPages
				&& totalPages > (totalInactivePage + 1)) {
			ul.appendChild(createPaginationLi("...", null));

			var page = totalPages;
			lastPage = page;
			text = page;
			href = "javascript:"+searchMethod+"(" + page + ",true)";
			ul.appendChild(createPaginationLi(text, href));
		}

		if ((pageNumber) < (totalItems / itemPerPage)) {
			text = "Next >";
			href = "javascript:"+searchMethod+"(" + (pageNumber + 1) + ",true)";
			ul.appendChild(createPaginationLi(text, href));

		}

		paginationSpan.appendChild(ul);

	}
}
function createPaginationLi(text,href,isActive){
	var li = document.createElement("li");
	var strong = document.createElement("strong");
	var text = document.createTextNode(text);
	
	if(href!=null&&href!=""){			
		a = document.createElement("a");			
		if(isActive){
			a.setAttribute("class","active");
		}else{
			a.href = href;
		}	
		strong.appendChild(text);
		a.appendChild(strong);
		li.appendChild(a);
	}else{
		strong.appendChild(text);
		li.appendChild(strong);		
	}
	return li;
}