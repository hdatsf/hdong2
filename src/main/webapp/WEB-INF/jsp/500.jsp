<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style>
body {color: #333; background: #fff; padding: 0; margin: 0; position: relative; min-width: 700px; font-family: arial; font-size: 12px }
p, form, ol, ul, li, dl, dt, dd, h3 {margin: 0; padding: 0; list-style: none }
#content_left {margin-bottom: 14px; padding-bottom: 5px; border-bottom: 1px solid #f3f3f3 }
#content_left {width: 540px; padding-left: 121px; padding-top: 5px }
.norsSuggest {display: inline-block; color: #333; font-family: arial; font-size: 13px; position: relative; } 
.norsTitle {font-size: 22px; font-family: Microsoft Yahei; font-weight: normal; color: #333; margin: 35px 0 25px 0; }
.norsTitle2 {font-family: arial; font-size: 13px; color: #666; }
.norsSuggest ol {margin-left: 47px; }
.norsSuggest li {margin: 13px 0; }
</style>


<div id="wrapper_wrapper">
    <div id="content_left">
        <div class="nors">
            <div class="norsSuggest">
                <h3 class="norsTitle">很抱歉，系统处理异常！</h3>
                <p class="norsTitle2">错误信息：</p>
                <ol>
                    <li>错误信息：${pageContext.exception}</li>
					<li>错误堆栈信息：</li>
					<c:forEach var="trace" items="${exception.stackTrace}">
						<li>${trace}</li>
					</c:forEach>
                </ol>
            </div>
        </div>
    </div>
</div>