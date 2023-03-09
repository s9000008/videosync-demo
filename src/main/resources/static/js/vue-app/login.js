const loginVue = new Vue({
	el: '#loginVue',
	data: {
		username:"",
		password:""
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		submit:function(){
			let response = this.$http.post(user_service.login,{username: this.username, password: this.password});
			response.then(function(response){
				let responseBody = response.body;
				this.httpCode = response.status;
				if(response.status == 200){
					let message = "登入成功";
					console.log(responseBody)
					if(!responseBody.success){
						message = responseBody.message;
						alert(message);
						history.go(0);
					}else{
						alert(message);
						document.location.href="/playlist.html";
					}
				}
			},function(){
				console.log("發生錯誤");
			})
		}
	},mounted:function(){
		console.log("TEST")
	}
})

