#!/bin/bash
echo "#!/bin/sh" > target/bacnet.sh
echo "java -jar $PWD/target/bacnet-commands-cli.jar \"\$@\"" >> target/bacnet.sh
chmod +x target/bacnet.sh
sudo mv target/bacnet.sh /usr/bin/bacnet
exec java -cp target/bacnet-commands-cli.jar picocli.AutoComplete -n bacnet -o target/bacnet_completion no.entra.bacnet.cli.Bacnet
echo Install Bacnet command to /etc/bash_completion.d/bacnet
sudo mv target/bacnet_completion /etc/bash_completion.d/bacnet_completion
. /etc/bash_completion.d/bacnet_completion
echo "Try This:"
echo "bacnet <TAB>"
