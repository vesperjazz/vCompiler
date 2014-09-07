library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity dig_sys is
Port ( A,B,C : in std_logic; X,Y,Z : out std_logic);
end dig_sys;
architecture D_flow of dig_sys is
begin
X <= A xor B xor C;
Y <= A and ( B xor C);
Z <= (A and B and C) or (B and C);
end D_flow;