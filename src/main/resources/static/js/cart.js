		//カート内の商品の数量の初期値1で合計金額を計算するメソッド
		//ページ読み込み、数量変更で発火
		$(function () {
			
			$(document).ready(total());
			$("select").on('change', total);

		function total() {
				var heiretu = [];
				for (var i = 0; i < $(".item_title").length; i++) {

					var item_price = $(".item_title").eq(i).data("price");
					var item_select = $(".item_title").eq(i).next("select").find("option:selected").data("num");

					heiretu.push(item_price * item_select);
				}

				var total = 0;
				for (var j = 0; j < heiretu.length; j++) {
					total += heiretu[j];
				}

				$("#item_price_total").val(total + "円");

			}
				

			});

