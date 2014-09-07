library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity Dev_Encrpt is
port (D_in : in std_logic_vector (4 downto 0); D_out : out std_logic_vector (2 downto 0) );
end Dev_Encrpt;
architecture BEH of Dev_Encrpt is
begin
process (D_in) -- work out another solution in dataflow style!!
begin
if D_in(3) = '0' and D_in(1) = '0' then D_out <= (D_in(4), D_in(2),D_in(0) );
elsif D_in(3) = '0' and D_in(1) = '1' then D_out <= (D_in(0), D_in(4),D_in(2) );
elsif D_in(3) = '1' and D_in(1) = '0' then D_out <= (D_in(2), D_in(0),D_in(4) );
else D_out <= ( not(D_in(4)) , not(D_in(2)), not( D_in(0)) );
end if;
end process;
end BEH;