# dummy1

A dummy for testing purposes

## Installation

Download from http://gitgub.com/ls4f/dummy1.

## Usage

$ java -cp "jaybird-full-3.0.0-SNAPSHOT.jar:dummy1-0.1.0-SNAPSHOT-standalone.jar" dummy1.core -r

Jaybird is left outside specifically so you could (if you wanted) rebuild it later and test what's up


## Options

  -p, --port PORT             3050       Firebird port
  -u, --user USER             SYSDBA     Firebird user
  -k, --key PASSWORD          masterkey  Firebird password
  -f, --backupfile file-path  test.fbk   The .fbk file to use for backup/restore
  -d, --database db-path      test.fdb
  -h, --host HOST             127.0.0.1  Hostname/IP of the Firebird host
  -l, --log logfile-path                 File to use for the verbose output of a restore operation
  -v, --verbose                          Verbose output of a restore operation (has no effect on backup). Will use stdout by default
  -b, --backup                           We would like to backup a databasse
  -r, --restore                          A restore function would take place
  -o, --replace                          A restore with enabled overwrite
  -g, --gzip                             Whether to use the built-in GZIP I/O streams in the JVM

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
