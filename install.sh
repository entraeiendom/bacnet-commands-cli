#!/bin/sh
echo "#!/bin/sh" > target/bacnet.sh
echo "java -jar $PWD/target/bacnet-commands-cli.jar"
chmod +x target/bacnet.sh
mv target/bacnet.sh /usr/bin/bacnet.sh
