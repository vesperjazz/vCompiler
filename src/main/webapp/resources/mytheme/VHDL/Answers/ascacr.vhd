LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY ascacr IS
END ascacr;

ARCHITECTURE behavior OF ascacr IS 

	COMPONENT combcct
	PORT(
		A : in STD_LOGIC_VECTOR(3 DOWNTO 0);
		Z : out STD_LOGIC);
	END COMPONENT;

	signal A : STD_LOGIC_VECTOR(3 DOWNTO 0) := (others => '0');
	signal Z : STD_LOGIC := '0';
BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: combcct PORT MAP (
		A => A,
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