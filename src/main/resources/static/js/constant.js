const api_base_url = "api/";
const service_code = {
	user:"user",
	video:"video",
	playlist:"playlist",
	subtitle:"subtitle"
}
const user_service = {
	reg:`${api_base_url}${service_code.user}/register`,
	login:`${api_base_url}${service_code.user}/login`,
	signOut:`${api_base_url}${service_code.user}/singOut`,
	def:`${api_base_url}${service_code.user}/`
};
const video_service = {
	all:`${api_base_url}${service_code.video}/all`,
	delete:`${api_base_url}${service_code.video}/delete`,
	update:`${api_base_url}${service_code.video}/update`,
	save:`${api_base_url}${service_code.video}/`,
	resource:`${api_base_url}${service_code.video}/resource/watch/`
};
const playlist_service = {
	all:`${api_base_url}${service_code.playlist}/all`,
	get:`${api_base_url}${service_code.playlist}/get`,
	add:`${api_base_url}${service_code.playlist}/add`,
	remove:`${api_base_url}${service_code.playlist}/remove`,
	sort:`${api_base_url}${service_code.playlist}/sort`,
	update:`${api_base_url}${service_code.playlist}/update`,
	save:`${api_base_url}${service_code.playlist}/`,
	resource:`${api_base_url}${service_code.video}/resource/watch`
};
const subtitle_service = {
	resource:`${api_base_url}${service_code.subtitle}/`,
	get:`${api_base_url}${service_code.subtitle}/get`,
	getSource:`${api_base_url}${service_code.subtitle}/getSubtitle`
};