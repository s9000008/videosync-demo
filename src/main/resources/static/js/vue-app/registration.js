const registrationVue = new Vue({
	el: '#registrationVue',
	data: {
		username:"",
		password:""
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		submit:function(){
			
			let response = this.$http.post(user_service.reg,{username: this.username, password: this.password});
			response.then(function(response){
				let responseBody = response.body;
				this.httpCode = response.status;
				if(response.status == 200){
					let message = "註冊成功";
					if(!responseBody.success){
						message = responseBody.message;
						history.go(0);
					}else{
						alert(message);
						document.location.href="/login.html";
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

