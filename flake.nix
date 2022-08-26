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
    in {
      devShells.default = pkgs.mkShell {
        packages = with pkgs; [
          bazel_5
          bazel-buildtools
          jdk11
          jdk
          nodePackages.cspell
        ];
      };
      shellHook = ''
        export JDK_HOME=${pkgs.jdk}
      '';
    });
}
