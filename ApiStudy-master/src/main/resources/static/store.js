// let index = {
//     init: function () {
//         $("#btn-save").on("click", () => {
//             this.save();
//         })
//         console.log("JS 실행")
//     },
//
//     save: function () {
//         let data = {
//             title: $("#title").val(),
//             content: $("#content").val(),
//         }
//
//         $.ajax({
//             type: "POST",
//             url: "/board/upload",
//             data: JSON.stringify(data),
//             contentType: "application/json; charset=utf-8",
//             dataType: "json"
//         }).done(function (res) {
//             alert("저장완료");
//             location.href = "/";
//         }).fail(function (error) {
//             alert(JSON.stringify(error));
//         });
//     },
// }
//
//
// // /upload 가면 GET 요청 실행
// // 추후에 리액트와 함께 쓰면, 렌더링도 가능
// function request() {
//     fetch('http://localhost:8080/store/1', {
//         method: 'GET',
//     })
//         .then(response => {
//             return response.json();
//         })
//         .then(data => {
//             console.log(data);
//         });
// }
// request();
//
//
//
// index.init();