./req.sh POST newuser.json user
./req.sh POST authenticate.json user/authenticate |tee /tmp/auth.log
cat /tmp/auth.log |sed "s/,/\n/g" > /tmp/auth1.log
USERID=`cat /tmp/auth1.log  |grep id |cut -d ":" -f 3`
SECRET=`cat /tmp/auth1.log  |grep secret |cut -d '"' -f 4`
echo $USERID:$SECRET > auth.txt
./req.sh POST newpoll.json poll auth.txt
./req.sh POST newchoice1.json choice auth.txt
./req.sh POST newchoice2.json choice auth.txt
./req.sh POST newchoice3.json choice auth.txt
./req.sh POST newchoice4.json choice auth.txt

