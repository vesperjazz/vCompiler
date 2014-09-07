LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY test IS
END test;

ARCHITECTURE behavior OF test IS 

	COMPONENT dig_sys
	PORT(
		A : in STD_LOGIC;
		B : in STD_LOGIC;
		C : in STD_LOGIC;
		X : out STD_LOGIC;
		Y : out STD_LOGIC;
		Z : out STD_LOGIC);
	END COMPONENT;

	signal A : STD_LOGIC := '0';
	signal B : STD_LOGIC := '0';
	signal C : STD_LOGIC := '0';
	signal X : STD_LOGIC := '0';
	signal Y : STD_LOGIC := '0';
	signal Z : STD_LOGIC := '0';
BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: dig_sys PORT MAP (
		A => A,
		B => B,
		C => C,
		X => X,
		Y => Y,
		Z => Z);
	-- Stimulus process
	stim_proc: process
	begin
		-- hold reset state for 100 ns.
		wait for 100 ns;
		-- insert stimulus here 






		wait;
	end process;
END behavior;