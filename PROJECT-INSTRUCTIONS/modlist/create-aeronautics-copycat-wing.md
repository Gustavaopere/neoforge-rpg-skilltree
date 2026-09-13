# Create Aeronautics: Copycat Wing

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8119be13d7e863dd3bb5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Copycat Wing
- **Arquivo JAR:** `CreateAeronauticsCopycatWing-1.21.1-1.0.4.jar`
- **Versão 1.21.1:** 1.0.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Visual
- **Função:** Faz blocos Copycats+ funcionarem como superfícies de asa/lift no sistema físico do Create Aeronautics.
- **Dependências:** Pack físico: Create 6.0.10 + Create Aeronautics 1.3.2 + Copycats+ 3.0.9 + Sable 2.0.5. Aerocopycats 1.1.1 (runtime 1.1.0) é complementar no mesmo domínio físico.
- **Sobreposição:** Complementa Aerocopycats: Copycat Wing controla semântica de lift/toggle; Aerocopycats cobre propriedades físicas/massa. Não são duplicatas globais.
- **Compatibilidade/Riscos:** Riscos: regression do toggle de lift corrigida em 1.0.4; blockstate stale em assemble/disassemble; Copycats+ 3.0.9/Aeronautics 1.3.2 API drift; interação concorrente; overlap physics com Aerocopycats sem equivalência funcional.
- **Observações:** JAR `CreateAeronauticsCopycatWing-1.21.1-1.0.4.jar`, mod id `copycat_wing`, runtime 1.0.4. Source matching confirma mod 1.0.4 e `LIFT_ENABLED`; a antiga observação runtime 1.0.2 foi corrigida.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial 1.0.4 + repositório oficial mumu17-git/CreateAeronauticsCopycatWing source matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-copycat-wing
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.4 com lift state, Honeycomb/Axe toggles, cobertura Copycats, Aerocopycats boundary, lifecycle e regressão 1.0.4 catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🪽 **Identidade física e source matching confirmados:** `CreateAeronauticsCopycatWing-1.21.1-1.0.4.jar`, mod id `copycat_wing`, runtime `1.0.4`. O source oficial declara exatamente MC 1.21.1/mod 1.0.4; a observação antiga de runtime 1.0.2 estava obsoleta.

## 1. Papel e authority
Create Aeronautics: Copycat Wing faz blocos Copycats funcionarem como superfícies aerodinâmicas de asa no Create Aeronautics. **Copycats+** continua owner da geometria/material copycat; **Create Aeronautics/Sable** continuam owners da física; este addon owns a tradução de determinados copycats para comportamento de lift e seu state de enable/disable.

## 2. Dependências concretas
O pack contém Create Aeronautics 1.3.2, Copycats+ 3.0.9, Create 6.0.10 e Sable 2.0.5. O source 1.0.4 foi desenvolvido com Aeronautics 1.2.1 e Copycats+ 3.0.4, portanto o pack está em revisões posteriores; isso exige regression test, não autoriza presumir incompatibilidade.

## 3. Cobertura de Copycats
A documentação oficial lista suporte a famílias de panel, step, slab, layer, half-panel, flat pane, half-layer, slope-layer, vertical-half-layer, stacked-half-layer, slice, vertical-slice, beam e vertical-step, combinando blocos Create e Copycats+.
Essa lista é a superfície publicada; blocos copycat de outros addons não são automaticamente considerados asas sem suporte explícito.

## 4. Lift como state do bloco
O source matching injeta uma propriedade de block state para indicar se o lift está habilitado. Portanto a ativação aerodinâmica não deve ser tratada como simples efeito visual ou atributo transitório do cliente.
Mudança de material copycat, rotate/mirror, assemble/disassemble e chunk reload precisam preservar o state correto.

## 5. Honeycomb — desabilitar lift
A linha 1.0.4 confirma interação server-side com Honeycomb para desligar o lift daquele bloco suportado. A operação atualiza o block state e fornece feedback de partículas/som.
O consumo/interação deve ocorrer uma única vez; client prediction não pode duplicar item ou inverter o state duas vezes.

## 6. Axe — restaurar lift
O source matching confirma uso de item pertencente à tag de axes para reabilitar o lift. A operação só deve mudar state quando o bloco possui a propriedade correspondente e está desabilitado.
Ferramentas externas que simulam interação precisam respeitar a mesma authority server-side.

## 7. Fix 1.0.4
O changelog exato da 1.0.4 corrige casos em que certos blocos Copycat não aplicavam corretamente as configurações de lift enable/disable.
Esse é o principal regression gate da build instalada: todas as famílias suportadas precisam responder consistentemente a Honeycomb/Axe e manter o resultado após reload.

## 8. Relação com Aerocopycats
O pack contém `aerocopycats-1.1.1.jar` com runtime metadata 1.1.0. A função documentada historicamente é complementar: Aerocopycats fornece propriedades físicas/massa de copycats no stack Sable/Aeronautics, enquanto Copycat Wing adiciona semântica aerodinâmica/lift.
Não remover um como duplicata do outro sem prova de cobertura integral das duas funções.

## 9. Assemble/disassemble
Quando um bloco copycat entra numa contraption física, sua geometria/material e seu lift-enabled state precisam ser capturados de maneira coerente. Desmontar não pode restaurar lift que o jogador havia desligado nem perder o material copycat.

## 10. Mudança de material
Trocar o material exibido por um copycat não deve, por si só, alterar a política de lift se o block state permanece o mesmo. Material visual e função aerodinâmica são superfícies distintas, salvo regra explícita do provider.

## 11. Client/server
Material/render/partículas são client-facing. O state de lift e seus efeitos físicos precisam convergir no servidor/physics stack.
O cliente não deve poder ativar uma asa apenas alterando render/modelo local.

## 12. Multiplayer
Dois jogadores usando Honeycomb/Axe simultaneamente sobre o mesmo bloco não podem produzir state divergente. Todos os clientes precisam receber o mesmo block state e a simulação física subsequente precisa usar esse state único.

## 13. Lifecycle
Testar place, material assignment, Honeycomb, Axe, rotate, mirror, copy/paste schematic se suportado, assemble, flight, disassemble, chunk unload/reload e restart. O state de lift precisa persistir em todas as transições em que o bloco persiste.

## 14. Riscos
1. Regressão 1.0.4: determinadas formas ignoram enable/disable.
2. Honeycomb é consumido duas vezes ou state não muda server-side.
3. Axe reabilita lift em bloco não suportado.
4. Assemble perde a propriedade de lift.
5. Disassemble restaura state antigo/stale.
6. Material copycat e lift state ficam acoplados indevidamente.
7. Aerocopycats e Copycat Wing aplicam physics hooks conflitantes.
8. Update de Copycats+ 3.0.9 muda block implementation esperada pelo mixin.
9. Update Aeronautics 1.3.2 altera interface de superfície aerodinâmica.
10. Cliente renderiza feedback incompatível com state físico real.

## 15. Matriz de testes
- [ ] Dedicated server inicia com 1.0.4 + Aeronautics 1.3.2 + Copycats+ 3.0.9 + Sable 2.0.5.
- [ ] Cada família publicada gera lift quando habilitada.
- [ ] Honeycomb desabilita lift exatamente uma vez.
- [ ] Axe restaura lift exatamente uma vez.
- [ ] Todas as formas cobertas pelo fix 1.0.4 respeitam o toggle.
- [ ] Chunk reload/restart preserva o state.
- [ ] Assemble/disassemble preserva material e lift-enabled.
- [ ] Aerocopycats 1.1.1 coexiste sem massa/lift duplicados.
- [ ] Dois clientes observam o mesmo state e comportamento físico.
- [ ] Mudança de material não reseta silenciosamente a política de lift.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.0.4. A publicação oficial confirma Release NeoForge 1.21.1, Client & Server, lista de formas e fix 1.0.4. O repositório oficial matching confirma `COPYCAT_WING$LIFT_ENABLED` e interações Honeycomb/Axe. Não foram inventados coeficientes de lift, massa ou fórmulas aerodinâmicas não verificadas.

> 🔒 **Boundary canônico:** Copycats+ owns forma/material; Copycat Wing owns o flag de uso aerodinâmico; Aeronautics/Sable own a simulação física. Render do copycat nunca substitui o state server-side de lift.
