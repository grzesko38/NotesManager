<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="utils" uri="http://arczynskiadam.pl/jsp/tlds/util" %>
<%@ taglib prefix="nav" uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>

<spring:theme code="theme.name" var="themeName"/>
<c:set var="isBlueTheme" value="${themeName eq 'blue'}" />
<c:set var="isRedTheme" value="${themeName eq 'red'}" />
<c:set var="isGreenTheme" value="${themeName eq 'green'}" />	
<c:set var="isYellowTheme" value="${themeName eq 'yellow'}" />

<!-- workaround for Eclipse validation bug -->
<c:set var="locale" value="${pageContext.response.locale}"/>
<!--end of workarround -->

<c:set var="isUKLocale" value="${locale eq 'en_EN'}" />
<c:set var="isDELocale" value="${locale eq 'de_DE'}" />
<c:set var="isPLLocale" value="${locale eq 'pl_PL'}" />
		
<div id="placeholder"></div>
<header class="top">
	<div class="topbar">
		<div class="dateholder">		
			<utils:date separator="/" />
		</div>
	</div>
	<div class="menu">
		<nav class="buttonsBar">
			<a href="?theme=blue">
				<c:choose>
					<c:when test="${isBlueTheme}">
						<span class="blue buttonBorder active">
							<span class="blue themeholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="blue buttonBorder">
							<span class="blue themeholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
			<a href="?theme=yellow">
				<c:choose>
					<c:when test="${isYellowTheme}">
						<span class="yellow buttonBorder active">
							<span class="yellow themeholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="yellow buttonBorder">
							<span class="yellow themeholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
			<a href="?theme=green">
				<c:choose>
					<c:when test="${isGreenTheme}">
						<span class="green buttonBorder active">
							<span class="green themeholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="green buttonBorder">
							<span class="green themeholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
			<a href="?theme=red">
				<c:choose>
					<c:when test="${isRedTheme}">
						<span class="red buttonBorder active">
							<span class="red themeholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="red buttonBorder">
							<span class="red themeholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
		</nav>
		<nav class="buttonsBar">
			<a href="?lang=en_EN">
				<c:choose>
					<c:when test="${isUKLocale}">
						<span class="buttonBorder active">
							<span class="uk flagholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="buttonBorder">
							<span class="uk flagholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
			<a href="?lang=de_DE">
				<c:choose>
					<c:when test="${isDELocale}">
						<span class="buttonBorder active">
							<span class="de flagholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="buttonBorder">
							<span class="de flagholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
			<a href="?lang=pl_PL">
				<c:choose>
					<c:when test="${isPLLocale}">
						<span class="buttonBorder active">
							<span class="pl flagholder active"></span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="buttonBorder">
							<span class="pl flagholder"></span>
						</span>
					</c:otherwise>
				</c:choose>
			</a>
		</nav>

	</div>
	<div class="clockHolder">
		<canvas class="clockBar" id="clock" width="75" height="75"></canvas>
	</div>
		<c:if test="${not empty navItems}">
		<div class="navbar">
		   <nav:navbar navigationItems="${navItems}" />
	   </div>
	</c:if>	
</header>

