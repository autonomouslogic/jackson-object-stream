#!/bin/bash -e

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

TAG="$(hg log --follow --template '{tags}\n' | egrep -v 'tip|^$' | head -n 1)"
REVTAG="$(hg parent --template '{tags}')"
DATE="$(hg log -l 1 --template '{date|shortdate}' | sed 's/-//g')"
HASH="$(hg log -l 1 --template '{node|short}')"

if [ "$TAG" == "$REVTAG" ]; then
	VERSION="$TAG"
else
	VERSION="$TAG-$DATE.${HASH:0:7}"
fi

echo $VERSION
(cd $DIR/.. ; npm version "$VERSION")
echo -n $VERSION > $DIR/../VERSION
