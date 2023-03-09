const headerVue = new Vue({
	el: '#headerVue',
	data: {
		username:null,
		uid:null,
		loginFlag:false,
		bar:{},
		ulCls:"navbar-nav ms-auto",
		liCls:"nav-item",
		items:[]
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		addBaseItem:function(){
			this.bar = {text:"TDB", href:"/index.html", target: "_self" , cls:"navbar-brand"};
		},
		addNonLoginItem:function(){
			//this.items.push({text:"影音", href:"/video-list.html", target: "_self" , cls:"nav-link"});
			this.items.push({text:"註冊", href:"/registration.html", target: "_self" , cls:"nav-link"});
			this.items.push({text:"登入", href:"/login.html", target: "_self" , cls:"nav-link"});
		},
		addLoginItem:function(){
			this.items.push({text:`Hi, ${this.username}`, href:"/", target: "_self" , cls:"nav-link"});
			this.items.push({text:"影音清單", href:"/video-list.html", target: "_self" , cls:"nav-link"});
			this.items.push({text:"播放清單", href:"/playlist.html", target: "_self" , cls:"nav-link"});
			this.items.push({text:"影音上傳", href:"/upload.html", target: "_self" , cls:"nav-link"});
			this.items.push({text:"登出", href:"javascript:logout();", target: "_self" , cls:"nav-link"});
		},
		logout:function(){
			if(this.loginFlag){
				let response = this.$http.get(user_service.singOut);
				response.then(function(response){
					console.log(response)
					let body = response.body;
					if(body.success){
						document.location.href="/";
					}else{
						
					}
				},function(){
				});
			}
		}
	},mounted:function(){
		this.addBaseItem();
		let response = this.$http.get(user_service.def);
		response.then(function(response){
			let body = response.body;
			if(body.success){
				this.username = body.username;
				this.uid = body.uid;
				this.loginFlag = true;
				this.addLoginItem();
			}else{
				this.addNonLoginItem();
				if(
					document.location.pathname.startsWith("/login.html") ||
					document.location.pathname.startsWith("/index.html") ||
					document.location.pathname.startsWith("/registration.html") ||
					document.location.pathname == "/"
					){
				}else{
					document.location.href = "/";
				}
			}
		},function(){
			console.log("尚未登入");
		});
	}
});
function logout(){
	headerVue.logout();
}

