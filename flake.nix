{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = {
    self,
    nixpkgs,
    flake-utils,
  }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = import nixpkgs {
        inherit system;
      };
    in let
      hooks = pkgs: {
        jdk = ''
          export JDK_HOME=${pkgs.jdk}
        '';
        postgres = ''
          export DBROOT="$(pwd)/.postgresql"
          export PGHOST=localhost
          export PGPORT=5432
          export PGDATA="$DBROOT/data"
          export PGDATABASE=local-postgres
          export PGUSER=$(whoami)
          export PGPASSWORD=postgres
          export LOG_PATH="$DBROOT/log"
          if [ ! -d "$DBROOT" ]; then
            echo "Creating PostgreSQL directory..."
            mkdir -p "$DBROOT"
          fi
          if [ ! -d "$PGDATA" ]; then
            echo "Initializing PostgreSQL database..."
            initdb $PGDATA --no-locale
          fi
          if [ ! -f "$DBROOT/.s.PGSQL.5432.lock" ]; then
            echo "Starting PostgreSQL database..."
            pg_ctl start -w \
              -l $LOG_PATH \
              -D $PGDATA
          fi
        '';
      };
    in {
      devShells.default = pkgs.mkShell {
        packages = with pkgs; [
          bazel_5
          bazel-buildtools
          jdk11
          jdk
          nodePackages.cspell
          postgresql_13
          alejandra
        ];
        shellHook = with pkgs.lib;
          concatStrings (attrValues (hooks pkgs));
      };
    });
}
