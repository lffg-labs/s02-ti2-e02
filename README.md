# `s02-ti2-e02`

School project.

This project is setup using Nix and Bazel. You may execute the main development
target by running:

1. `nix develop`, to enter in the development shell;
2. `bazel run //:Main`, to invoke the main target.

Build the database schema by executing `psql -f sql/creation.sql`.

- I don't think we need migrations here. I am lazy.
