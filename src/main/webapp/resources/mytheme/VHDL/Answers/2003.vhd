library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity customized_mux is
port( A, B, C, D,E : in std_logic_vector(3 downto 0);
S : in std_logic_vector(2 downto 0);
T : out std_logic_vector(3 downto 0) );
end customized_mux;

architecture prog1 of customized_mux is -- architecture 1
begin -- in dataflow style of modeling
with S select
T <= A when "000",
B when "001",
A when "010",
C when "011",
A when "100",
D when "101",
A when "110",
E when others;
end prog1;