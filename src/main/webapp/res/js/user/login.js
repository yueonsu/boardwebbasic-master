var frm = document.querySelector('#frm');
var btn = document.querySelector('#btnShowPw');
if(frm) {
    frm.addEventListener('submit', function(e){
        if(frm.uid.value.length<5 || frm.uid.value.length > 20) {
            alert('아이디는 5~20 글자 입니다.');
            e.preventDefault();

        } else if(frm.upw.value.length < 5) {
            alert('비밀번호를 확인해 주세요.');
            e.preventDefault();
        }
    });
}
if(btn){
    btn.addEventListener('click', function (e) {
       if(frm.upw.type === 'password') {
           frm.upw.type = 'text'; // 주소값을 불러와서 그 주소값의 타입을 바꿈
           btnShowPw.value = '비밀번호 숨기기';
       } else {
           frm.upw.type = 'password';
           btnShowPw.value = '비밀번호 보이기';
       }
    });
}