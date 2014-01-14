mod_MarchingCube
================

Custom Minecraft blocks to implement and test the render of marching cubes and other filler type blocks.

E.g.<br>
http://paulbourke.net/geometry/polygonise/<br>
http://en.wikipedia.org/wiki/Marching_cubes<br>

Requires MC 1.4.5, MCP 723, and Forge 6.4.1 (411)

http://mcp.ocean-labs.de/index.php/MCP_Releases<br>
http://www.minecraftforge.net/forum/<br>

Relevant links;<br>
http://binarymage.com/ - XCompWiz's Dev Blog (Mystcraft)


<script src="http://coinwidget.com/widget/coin.js"></script>
<script>
CoinWidgetCom.go({
	wallet_address: "1Bj7r8oEwkgo4f1f52ACAvD1n3KufzrbtD"
	, currency: "bitcoin"
	, counter: "count"
	, alignment: "bl"
	, qrcode: true
	, auto_show: false
	, lbl_button: "Donate"
	, lbl_address: "My Bitcoin Address:"
	, lbl_count: "donations"
	, lbl_amount: "BTC"
});
</script>



<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript" src="https://blockchain.info//Resources/wallet/pay-now-button.js"></script>
<div style="font-size:16px;margin:0 auto;width:300px" class="blockchain-btn"
     data-address="1Bj7r8oEwkgo4f1f52ACAvD1n3KufzrbtD"
     data-shared="false">
    <div class="blockchain stage-begin">
        <img src="https://blockchain.info//Resources/buttons/donate_64.png"/>
    </div>
    <div class="blockchain stage-loading" style="text-align:center">
        <img src="https://blockchain.info//Resources/loading-large.gif"/>
    </div>
    <div class="blockchain stage-ready">
         <p align="center">Please Donate To Bitcoin Address: <b>[[address]]</b></p>
         <p align="center" class="qr-code"></p>
    </div>
    <div class="blockchain stage-paid">
         Donation of <b>[[value]] BTC</b> Received. Thank You.
    </div>
    <div class="blockchain stage-error">
        <font color="red">[[error]]</font>
    </div>
</div>
