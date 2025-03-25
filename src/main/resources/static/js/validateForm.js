
		function validateForm() {
			var password = document.getElementById("password").value;
			var confirmPassword = document.getElementById("confirm_password").value;
			var errorMessage = document.getElementById("error-message");

			if (password !== confirmPassword) {
				errorMessage.textContent = "パスワードが一致しません。もう一度入力してください。";
				return false;
			}

			errorMessage.textContent = ""; // エラーメッセージを消す
			return true;
		}
		
		
		
		/*             郵便番号api                */
		const API_URL = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=";

		const format = {
		  zip1: /^\d{3}-?\d{4}$/, // 郵便番号(ハイフン有り)
		  zip2: /^0\d{7}$/, // 郵便番号(ハイフン無し)
		};

		const searchBtn = document.getElementById("zipSearchBtn"), // 検索ボタン
		  zip = document.getElementById("zip_code"), // 郵便番号入力欄
		  address = document.getElementById("address"); // 住所入力欄

		const getAddress = (text) => {
		  const url = API_URL + text;
		  fetch(url)
		    .then((data) => {
		      return data.json();
		    })
		    .then((data) => {
		      if (data.results != null) {
		        address.value = `${data.results[0].address1}${data.results[0].address2}${data.results[0].address3}`;
		      } else {
		        alert("該当する住所が見つかりませんでした。");
		      }
		    })
		    .catch((error) => {
		      alert("住所検索に失敗しました。");
		    });
		};

		searchBtn.addEventListener("click", (e) => {
		  console.log(zip.value);
		  if (zip.value != "") {
		    // 郵便番号の形式か確認
		    if (format.zip1.test(zip.value) || format.zip2.test(zip.value)) {
		      getAddress(zip.value);
		    } else {
		      alert("郵便番号の形式で入力してください。");
		    }
		  } else {
		    alert("郵便番号を入力してください。");
		  }
		});