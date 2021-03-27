#!/bin/sh
echo "#!/bin/sh" > target/bacnet.sh
echo "java -jar $PWD/target/bacnet-commands-cli.jar \"\$@\"" >> target/bacnet.sh
chmod +x target/bacnet.sh
sudo mv target/bacnet.sh /usr/bin/bacnet
