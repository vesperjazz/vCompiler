library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity count01_beh is
Port (x, clk, rst : in std_logic; y : out std_logic);
end count01_beh;
architecture count01_beh_arch of count01_beh is
begin
process( clk , rst) -- assume that x synchronously changes with clock
variable zeros_count: std_logic_vector (1 downto 0):= "00"; -- zeros_count
variable ones_count: std_logic_vector (1 downto 0):= "00"; -- ones_count
begin
if (rst = '1') then y <= '0'; zeros_count := "00"; ones_count := "00";
elsif (clk'event and clk = '1') then
y <= '0'; --assigning a default value; else y will be ¡®U¡¯ until it becomes 1 or is reset.
if (x = '0') and (zeros_count<"11") then zeros_count := zeros_count +'1';
elsif (x = '1') and (ones_count<"11")then ones_count := ones_count + '1';
end if;
if (zeros_count = "11" and ones_count = "11") then y <= '1'; end if;
end if;
end process;
end count01_beh_arch;